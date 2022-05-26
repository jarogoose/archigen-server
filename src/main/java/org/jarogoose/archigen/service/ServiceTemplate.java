package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class ServiceTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/api/service.template";
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
    String dependencyBlockPath = "src/main/resources/template/api/service-dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    dependencyBlock = dependencyBlock.replace("{{class-name}}", capitalize(domain.feature()));

    return dependencyBlock;
  }

  public CharSequence createApiBlock(Domain domain) {
    String serviceReadApiBlockPath = "src/main/resources/template/api/service-read-api-block.template";
    String serviceWriteApiBlockPath = "src/main/resources/template/api/service-write-api-block.template";

    StringBuilder content = new StringBuilder();

    for (Request request : domain.controller().requests()) {
      if (request.type().equalsIgnoreCase("get")) {
        String apiBlock = readFile(serviceReadApiBlockPath, Charsets.UTF_8);

        // domain class
        String domainClass = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace("{{domain-class}}", domainClass);

        // service api name
        String serviceApiName = format("%s", request.api());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        // storage query api name
        String storageQueryApiName = format("%s", request.api());
        apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

        content.append(apiBlock).append(System.lineSeparator());

      } else {
        String apiBlock = readFile(serviceWriteApiBlockPath, Charsets.UTF_8);

        // domain class
        String domainClass = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace("{{domain-class}}", domainClass);

        // service api name
        String serviceApiName = format("%s", request.api());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        // storage query api name
        String storageQueryApiName = format("%s", request.api());
        apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

        content.append(apiBlock).append(System.lineSeparator());
      }
    }

    return content.toString();
  }
}
