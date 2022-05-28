package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
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

public class ServiceTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/api/service.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // dto import
    imports().addServiceImports(Commons.formatDtoImport(domain));

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), API_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getServiceImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlockPath = "src/main/resources/template/api/service-dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

    // loader import
    imports().addServiceImports(Commons.formatLoaderImport(domain));

    return dependencyBlock;
  }

  public CharSequence createApiBlock(Domain domain) {
    String serviceReadApiBlockPath = "src/main/resources/template/api/service-read-api-block.template";
    String serviceWriteApiBlockPath = "src/main/resources/template/api/service-write-api-block.template";

    StringBuilder content = new StringBuilder();

    for (Request request : domain.api().requests()) {
      if (request.type().equalsIgnoreCase("get")) {
        String apiBlock = readFile(serviceReadApiBlockPath, Charsets.UTF_8);

        // feature name
        String featureName = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

        // service api name
        String serviceApiName = format("%s", request.execute());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        // storage query api name
        String storageQueryApiName = format("%s", request.query());
        apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

        content.append(apiBlock).append(System.lineSeparator());

      } else {
        String apiBlock = readFile(serviceWriteApiBlockPath, Charsets.UTF_8);

        // feature name
        String featureName = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

        // service api name
        String serviceApiName = format("%s", request.execute());
        apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

        // storage query api name
        String storageQueryApiName = format("%s", request.query());
        apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

        content.append(apiBlock).append(System.lineSeparator());
      }
    }

    return content.toString();
  }
}
