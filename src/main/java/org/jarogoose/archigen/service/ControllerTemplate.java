package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.CONTROLLER_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class ControllerTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/control/controller.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // domain uri
    String domainUri = domain.root();
    template = template.replace("{{domain-uri}}", domainUri);

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), CONTROLLER_PACKAGE);
    template = template.replace("{{package}}", packageName);
    template = template.replace("{{class-name}}", capitalize(domain.feature()));
    template = template.replace("{{dependency-block}}", createDependencyBlock(domain));
    template = template.replace("{{api-block}}", createApiBlock(domain));

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlockPath = "src/main/resources/template/control/dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    dependencyBlock = dependencyBlock.replace("{{class-name}}", capitalize(domain.feature()));

    return dependencyBlock;
  }

  public String createApiBlock(Domain domain) {
    String apiBlockPath = "src/main/resources/template/control/api-block.template";
    StringBuilder content = new StringBuilder();

    for (Request request : domain.api().requests()) {
      String apiBlock = readFile(apiBlockPath, Charsets.UTF_8);

      // uri
      String uri = format("@%sMapping(\"%s\")",
          capitalize(request.type().toLowerCase()), formatUri(request.control()));
      apiBlock = apiBlock.replace("{{uri}}", uri);

      // function name
      String functionNme = format("%s", request.control());
      apiBlock = apiBlock.replace("{{function-name}}", functionNme);

      String controllerInput = format("%sRequest request", capitalize(request.control()));
      apiBlock = apiBlock.replace("{{controller-input}}", controllerInput);

      String facadeCall = null;
      // facade call
      if (request.type().equalsIgnoreCase("get")) {
        String action = format("%s", request.control());
        facadeCall = format("%sResponse response = facade.%s",
            capitalize(domain.feature()), action);
      } else {
        facadeCall = format("facade.%s", request.control());
      }

      apiBlock = apiBlock.replace("{{facade-call}}", facadeCall);
      apiBlock = apiBlock.replace("{{feature}}", capitalize(domain.feature()));

      content.append(apiBlock).append(System.lineSeparator());
    }

    return content.toString();
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
