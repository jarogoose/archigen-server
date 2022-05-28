package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatDtoImport;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import java.util.List;
import org.jarogoose.archigen.domain.Domain;

public class EntityMapperTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/storage/entity-mapper.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // package
    String packageName = String.format("%s.%s.%s",
        ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);

    // dto import
    imports().addEntityMapperImports(formatDtoImport(domain));

    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{dto-to-entity-block}}", createDtoToEntityBlock(domain));
    template = template.replace("{{entity-to-dto-block}}", createEntityToDtoBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getEntityMapperImportsFacadeImports());

    return template;
  }

  private String createDtoToEntityBlock(Domain domain) {
    String filePath = "src/main/resources/template/storage/dto-to-entity-block.template";
    String template = readFile(filePath, Charsets.UTF_8);
    String mapPattern = ".%s(dto.get%s())";

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{data-map-block}}", iterateData(domain.data(), mapPattern));

    return template;
  }

  private String createEntityToDtoBlock(Domain domain) {
    String filePath = "src/main/resources/template/storage/entity-to-dto-block.template";
    String template = readFile(filePath, Charsets.UTF_8);
    String mapPattern = ".%s(entity.get%s())";

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{data-map-block}}", iterateData(domain.data(), mapPattern));

    return template;
  }

  private String iterateData(List<String> data, String mapPattern) {
    // data block
    StringBuilder dataMapBlock = new StringBuilder();
    for (int i = 0; i < data.size(); i++) {
      dataMapBlock.append(format(mapPattern, data.get(i), capitalize(data.get(i))));
      if (i != data.size() - 1) {
        dataMapBlock.append(System.lineSeparator());
      }
    }
    return dataMapBlock.toString();
  }
}
