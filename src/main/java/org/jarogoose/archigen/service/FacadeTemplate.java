package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.service.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Commons.formatRequestImport;
import static org.jarogoose.archigen.util.Commons.formatResponseImport;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class FacadeTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/api/facade.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), API_PACKAGE);
    template = template.replace("{{package}}", packageName);
    template = template.replace("{{class-name}}", capitalize(domain.feature()));
    template = template.replace("{{dependency-block}}", createDependencyBlock(domain));
    template = template.replace("{{api-block}}", createApiBlock(domain));
    template = template.replace("{{imports}}", imports().getFacadeImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlockPath = "src/main/resources/template/api/facade-dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    dependencyBlock = dependencyBlock.replace("{{class-name}}", capitalize(domain.feature()));

    return dependencyBlock;
  }

  public String createApiBlock(Domain domain) {
    String facadeReadApiBlockPath = "src/main/resources/template/api/facade-read-api-block.template";
    String facadeWriteApiBlockPath = "src/main/resources/template/api/facade-write-api-block.template";

    // response import
    imports().addFacadeImport(formatResponseImport(domain));

    StringBuilder content = new StringBuilder();

    for (Request request : domain.api().requests()) {
      if (request.type().equalsIgnoreCase("get")) {
        String apiBlock = readFile(facadeReadApiBlockPath, Charsets.UTF_8);

        // domain name
        String domainName = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace("{{domain-class}}", domainName);

        // facade api name
        String facadeApiName = format("%s", request.control());
        apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

        // request name
        String requestName = format("%s", capitalize(request.control()));
        apiBlock = apiBlock.replace("{{request-name}}", requestName);

        // request import
        imports().addFacadeImport(formatRequestImport(domain, request));

        // service api name
        String serviceApiName = format("%s", request.execute());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        content.append(apiBlock).append(System.lineSeparator());

      } else {
        String apiBlock = readFile(facadeWriteApiBlockPath, Charsets.UTF_8);

        // facade api name
        String facadeApiName = format("%s", request.control());
        apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

        // request name
        String requestName = format("%s", capitalize(request.control()));
        apiBlock = apiBlock.replace("{{request-name}}", requestName);

        // request import
        imports().addFacadeImport(formatRequestImport(domain, request));

        // service api name
        String serviceApiName = format("%s", request.execute());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        content.append(apiBlock).append(System.lineSeparator());
      }
    }

    return content.toString();
  }

}
