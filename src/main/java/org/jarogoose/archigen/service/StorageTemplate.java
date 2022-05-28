package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class StorageTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/storage/storage.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(API.toString(), createApiBlock(domain));

    return template;
  }

  public CharSequence createApiBlock(Domain domain) {
    String storageReadApiBlockPath = "src/main/resources/template/storage/storage-read-api-block.template";
    StringBuilder content = new StringBuilder();

    for (Request request : domain.api().requests()) {
      if (request.customQuery()) {
        String apiBlock = readFile(storageReadApiBlockPath, Charsets.UTF_8);

        // feature name
        String featureName = format("%s", capitalize(domain.feature()));
        apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

        // query name
        String queryName = format("%s", request.query());
        apiBlock = apiBlock.replace("{{query-name}}", queryName);

        content.append(apiBlock);
      }
    }

    return content.toString();
  }
}
