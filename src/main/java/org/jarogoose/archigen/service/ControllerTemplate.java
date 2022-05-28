package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatNotFoundExceptionImport;
import static org.jarogoose.archigen.util.Commons.formatRequestImport;
import static org.jarogoose.archigen.util.Commons.formatResponseImport;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.CONTROLLER_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.DEPENDENCY;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.Commons;
import org.jarogoose.archigen.util.RequestType;

public class ControllerTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/control/controller.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // domain uri
    String domainUri = domain.root();
    template = template.replace("{{domain-uri}}", domainUri);

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), CONTROLLER_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getControllerImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlockPath = "src/main/resources/template/control/dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    // feature name
    String featureName = capitalize(domain.feature());
    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), featureName);

    // facade import
    imports().addControllerImport(Commons.formatFacadeImport(domain));

    return dependencyBlock;
  }

  public String createApiBlock(Domain domain) {
    String apiBlockPath = "src/main/resources/template/control/api-block.template";
    StringBuilder content = new StringBuilder();

    for (Request request : domain.api().requests()) {
      String apiBlock = readFile(apiBlockPath, Charsets.UTF_8);

      // uri
      String[] arr = request.type().split("_");
      String uri = format("@%sMapping(\"%s\")", capitalize(arr[0].toLowerCase()),
          formatUri(request.control()));
      apiBlock = apiBlock.replace("{{uri}}", uri);

      // function name
      String functionNme = format("%s", request.control());
      apiBlock = apiBlock.replace("{{function-name}}", functionNme);

      // controller input
      String controllerInput = format("%sRequest request", capitalize(request.control()));
      apiBlock = apiBlock.replace("{{controller-input}}", controllerInput);

      // request import
      imports().addControllerImport(formatRequestImport(domain, request));

      // facade call
      apiBlock = apiBlock.replace("{{facade-call}}", formatFacadeCall(domain, request));

      // exception name
      apiBlock = apiBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

      // import exception
      imports().addControllerImport(formatNotFoundExceptionImport(domain));

      content.append(apiBlock).append(System.lineSeparator());
    }

    return content.toString();
  }

  private String formatFacadeCall(Domain domain, Request request) {
    String facadeCall = "";
    if (request.type().equalsIgnoreCase(RequestType.GET.toString())) {
      facadeCall = format("%sResponse response = facade.%s",
          capitalize(domain.feature()), request.control());

      // response import
      imports().addControllerImport(formatResponseImport(domain));
    } else if (request.type().equalsIgnoreCase(RequestType.GET_ALL.toString())) {
      facadeCall = format("List<%sResponse> response = facade.%s",
          capitalize(domain.feature()), request.control());

      // response import
      imports().addControllerImport(formatResponseImport(domain));
    } else {
      facadeCall = format("facade.%s", request.control());
    }

    return facadeCall;
  }

  private String formatUri(String feature) {
    String[] words = feature.split("(?=\\p{Upper})");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word.toLowerCase()).append("-");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
