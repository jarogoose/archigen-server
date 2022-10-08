package org.jarogoose.archigen.core.template.storage;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;
import java.util.List;

import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.util.Paths;
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
    {{dto-data}}
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
    {{entity-data}}
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
    final String projectPath = String.format(
      "%s.%s",
      config.artefact(),
      config.project()
    );
    final String featureName = capitalize(domain.feature());

    String template = TEMPLATE;

    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{feature-name}}", featureName);
    template = template.replace("{{dto-data}}", formatDtoData(domain.data()));
    template =
      template.replace("{{entity-data}}", formatEntityData(domain.data()));

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.STORAGE_PATH
      .get(config, domain.root(), domain.feature(), "EntityMapper"));
  }

  private String formatDtoData(List<String> data) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    .")
        .append(field)
        .append("(entity.get")
        .append(capitalize(field))
        .append("())")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

  private String formatEntityData(List<String> data) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    .")
        .append(field)
        .append("(dto.")
        .append(field)
        .append("())")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }
}
