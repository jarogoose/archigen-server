package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatRequestImport;
import static org.jarogoose.archigen.util.Commons.formatResponseImport;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.DEPENDENCY;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.jarogoose.archigen.util.Replacer.REQUEST;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.Commons;
import org.jarogoose.archigen.util.ReturnType;
import org.springframework.http.HttpMethod;

public class FacadeTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/api/facade.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // mapper static import
    imports().addFacadeImport(Commons.formatRequestToDtoStaticImport(domain));
    imports().addFacadeImport(Commons.formatDtoToResponseStaticImport(domain));

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), API_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getFacadeImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlockPath = "src/main/resources/template/api/facade-dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

    return dependencyBlock;
  }

  public String createApiBlock(Domain domain) {
    // response import
    imports().addFacadeImport(formatResponseImport(domain));

    StringBuilder content = new StringBuilder();
    for (Request request : domain.requests()) {
      if (request.returnType().equalsIgnoreCase(ReturnType.OBJECT.name())) {
        formatReadFacadeApi(domain, request, content);
      } else if (request.returnType().equalsIgnoreCase(ReturnType.COLLECTION.name())) {
        formatReadAllFacadeApi(domain, request, content);
      } else {
        formatWriteFacadeApi(domain, request, content);
      }
    }

    return content.toString();
  }

  private void formatReadFacadeApi(Domain domain, Request request, StringBuilder content) {
    String facadeReadApiBlockPath = "src/main/resources/template/api/facade-read-api-block.template";
    String apiBlock = readFile(facadeReadApiBlockPath, Charsets.UTF_8);

    // domain name
    String domainName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), domainName);

    // facade api name
    String facadeApiName = format("%s", request.control());
    apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

    // request name
    String requestName = format("%s", capitalize(request.control()));
    apiBlock = apiBlock.replace(REQUEST.toString(), requestName);

    // request import
    imports().addFacadeImport(formatRequestImport(domain, request));

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatReadAllFacadeApi(Domain domain, Request request, StringBuilder content) {
    String template = "src/main/resources/template/api/facade-read-all-api-block.template";
    String apiBlock = readFile(template, Charsets.UTF_8);

    // domain name
    String domainName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), domainName);

    // facade api name
    String facadeApiName = format("%s", request.control());
    apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

    // request name
    String requestName = format("%s", capitalize(request.control()));
    apiBlock = apiBlock.replace(REQUEST.toString(), requestName);

    // request import
    imports().addFacadeImport(formatRequestImport(domain, request));

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatWriteFacadeApi(Domain domain, Request request, StringBuilder content) {
    String facadeWriteApiBlockPath = "src/main/resources/template/api/facade-write-api-block.template";
    String apiBlock = readFile(facadeWriteApiBlockPath, Charsets.UTF_8);

    // facade api name
    String facadeApiName = format("%s", request.control());
    apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

    // request name
    String requestName = format("%s", capitalize(request.control()));
    apiBlock = apiBlock.replace(REQUEST.toString(), requestName);

    // request import
    imports().addFacadeImport(formatRequestImport(domain, request));

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }
}
