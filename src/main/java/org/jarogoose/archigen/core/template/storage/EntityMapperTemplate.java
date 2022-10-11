package org.jarogoose.archigen.core.template.storage;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class EntityMapperTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.storage;

  import {{project-path}}.feature.{{root-name}}.domain.model.dto.{{feature-name}};
  import java.util.ArrayList;
  import java.util.List;
  import lombok.experimental.UtilityClass;

  @UtilityClass
  class {{feature-name}}EntityMapper {

    static {{feature-name}} toDto(final {{feature-name}}Entity entity) {
      return {{feature-name}}
        .builder()
        .id(entity.getId())
    {{entity-to-dto-data}}
        .build();
    }

    static List<{{feature-name}}> toDtos(final List<{{feature-name}}Entity> entities) {
      final List<{{feature-name}}> dtos = new ArrayList<>();

      for (final {{feature-name}}Entity entity : entities) {
        dtos.add(toDto(entity));
      }

      return dtos;
    }

    static {{feature-name}}Entity toEntity(final {{feature-name}} dto) {
      return {{feature-name}}Entity
        .builder()
        .id(dto.id() == null ? null : dto.id())
    {{dto-to-entity-data}}
        .build();
    }

    static List<{{feature-name}}Entity> toEntities(final List<{{feature-name}}> dtos) {
      final List<{{feature-name}}Entity> entities = new ArrayList<>();

      for (final {{feature-name}} dto : dtos) {
        entities.add(toEntity(dto));
      }

      return entities;
    }
  }

  """;

  private final Config config;
  private final Domain domain;

  public EntityMapperTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    String template = TEMPLATE;

    template =
      replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());
    template = replaceEntityToDtoData(template, domain.data());
    template = replaceDtoToEntityData(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.STORAGE_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "EntityMapper",
        false
      )
    );
  }
}
