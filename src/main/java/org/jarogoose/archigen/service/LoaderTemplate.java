package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.DEPENDENCY;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.Replacer;

public class LoaderTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/storage/loader.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlockPath = "src/main/resources/template/storage/loader-dependency-block.template";
    String dependencyBlock = readFile(dependencyBlockPath, Charsets.UTF_8);

    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

    return dependencyBlock;
  }

  public CharSequence createApiBlock(Domain domain) {
    String serviceReadApiBlockPath = "src/main/resources/template/storage/loader-read-api-block.template";
    String serviceWriteApiBlockPath = "src/main/resources/template/storage/loader-write-api-block.template";

    StringBuilder content = new StringBuilder();

    for (Request request : domain.api().requests()) {
      if (request.type().equalsIgnoreCase("get")) {
        String apiBlock = readFile(serviceReadApiBlockPath, Charsets.UTF_8);

        // feature name
        String featureName = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

        // storage query api name
        String storageQueryApiName = format("%s", request.query());
        apiBlock = apiBlock.replace("{{query-name}}", storageQueryApiName);

        // root name
        apiBlock = apiBlock.replace("{{root-name}}", domain.root().toUpperCase());

        // domain text
        apiBlock = apiBlock.replace(FEATURE.toString(), formatDomainText(domain.feature()));

        content.append(apiBlock).append(System.lineSeparator());

      } else {
        String apiBlock = readFile(serviceWriteApiBlockPath, Charsets.UTF_8);

        // feature class
        String featureClass = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace(FEATURE.toString(), featureClass);

        // storage query api name
        String storageQueryApiName = format("%s", request.query());
        apiBlock = apiBlock.replace("{{query-name}}", storageQueryApiName);

        content.append(apiBlock).append(System.lineSeparator());
      }
    }

    return content.toString();
  }

  private String formatDomainText(String feature) {
    String[] words = feature.split("(?=\\p{Upper})");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word.toLowerCase()).append(" ");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
