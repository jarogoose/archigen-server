package org.jarogoose.archigen.service;

import static java.lang.String.format;
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

    // domain uri
    String domainUri = domain.root();
    template = template.replace("{{domain-uri}}", domainUri);

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), API_PACKAGE);
    template = template.replace("{{package}}", packageName);
    template = template.replace("{{class-name}}", capitalize(domain.feature()));
    template = template.replace("{{dependency-block}}", createDependencyBlock(domain));
    template = template.replace("{{api-block}}", createApiBlock(domain));

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

    StringBuilder content = new StringBuilder();

    for (Request request : domain.controller().requests()) {
      if (request.type().equalsIgnoreCase("get")) {
        String apiBlock = readFile(facadeReadApiBlockPath, Charsets.UTF_8);

        // domain name
        String domainName = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace("{{domain-class}}", domainName);

        // facade api name
        String facadeApiName = format("%s", request.name());
        apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

        // request name
        String requestName = format("%s", capitalize(request.name()));
        apiBlock = apiBlock.replace("{{request-name}}", requestName);

        // service api name
        String serviceApiName = format("%s", request.api());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        content.append(apiBlock).append(System.lineSeparator());

      } else {
        String apiBlock = readFile(facadeWriteApiBlockPath, Charsets.UTF_8);

        // facade api name
        String facadeApiName = format("%s", request.name());
        apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

        // request name
        String requestName = format("%s", capitalize(request.name()));
        apiBlock = apiBlock.replace("{{request-name}}", requestName);

        // service api name
        String serviceApiName = format("%s", request.api());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        content.append(apiBlock).append(System.lineSeparator());
      }
    }

    return content.toString();
  }

}
