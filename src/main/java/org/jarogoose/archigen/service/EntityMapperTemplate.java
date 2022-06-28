package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatDtoImport;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import java.util.List;
import org.jarogoose.archigen.domain.Domain;

public class EntityMapperTemplate {

  public static final String TEMPLATE = """
      package {{package}};
            
      {{imports}}
      import java.util.ArrayList;
      import java.util.List;
            
      class {{feature-name}}EntityMapper {
            
      {{dto-to-entity-block}}
      {{entity-to-dto-block}}
      {{entities-to-dtos-block}}
      }
      """;

  public static final String DTO_TO_ENTITY_BLOCK_TEMPLATE = """
        public static {{feature-name}}Entity toEntity({{feature-name}} dto) {
          return {{feature-name}}Entity.builder()
              .id(dto.id() == null ? null : dto.id())
      {{data-map-block}}
              .build();
        }
      """;

  public static final String ENTITY_TO_DTO_BLOCK_TEMPLATE = """
        public static {{feature-name}} toDto({{feature-name}}Entity entity) {
          return {{feature-name}}.builder()
              .id(entity.getId())
      {{data-map-block}}
              .build();
        }
      """;

  public static final String ENTITIES_TO_DTOS_BLOCK_TEMPLATE = """
        public static List<{{feature-name}}> toDto(List<{{feature-name}}Entity> entities) {
          List<{{feature-name}}> dtos = new ArrayList<>();
          for ({{feature-name}}Entity entity : entities) {
            dtos.add(toDto(entity));
          }
          return dtos;
        }
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

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
    template = template.replace("{{entities-to-dtos-block}}", createEntitiesToDtosBlock(domain));
    template = template.replace(IMPORTS.toString(),
        imports().getEntityMapperImportsFacadeImports());

    return template;
  }

  private String createEntitiesToDtosBlock(Domain domain) {
    return ENTITIES_TO_DTOS_BLOCK_TEMPLATE.replace("{{feature-name}}", capitalize(domain.feature()));
  }

  private String createDtoToEntityBlock(Domain domain) {
    String template = DTO_TO_ENTITY_BLOCK_TEMPLATE;
    String mapPattern = "        .%s(dto.%s())";

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{data-map-block}}",
        iterateDtoToEntityData(domain.data(), mapPattern));

    return template;
  }

  private String createEntityToDtoBlock(Domain domain) {
    String template = ENTITY_TO_DTO_BLOCK_TEMPLATE;
    String mapPattern = "        .%s(entity.get%s())";

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{data-map-block}}",
        iterateEntityToDtoData(domain.data(), mapPattern));

    return template;
  }

  private String iterateDtoToEntityData(List<String> data, String mapPattern) {
    // data block
    StringBuilder dataMapBlock = new StringBuilder();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).equalsIgnoreCase("id")) {
        continue;
      }
      dataMapBlock.append(format(mapPattern, data.get(i), data.get(i)));
      if (i != data.size() - 1) {
        dataMapBlock.append(System.lineSeparator());
      }
    }
    return dataMapBlock.toString();
  }

  private String iterateEntityToDtoData(List<String> data, String mapPattern) {
    // data block
    StringBuilder dataMapBlock = new StringBuilder();
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).equalsIgnoreCase("id")) {
        continue;
      }
      dataMapBlock.append(format(mapPattern, data.get(i), capitalize(data.get(i))));
      if (i != data.size() - 1) {
        dataMapBlock.append(System.lineSeparator());
      }
    }
    return dataMapBlock.toString();
  }
}
