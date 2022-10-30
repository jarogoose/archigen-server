package org.jarogoose.archigen.core.template.testing;

import static org.springframework.util.StringUtils.capitalize;

import java.io.File;
import java.util.List;

import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class EntityMapperUnitTestTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
      package {{project-path}}.feature.{{root-name}}.storage;

      import static org.assertj.core.api.Assertions.assertThat;
      import static org.junit.jupiter.api.Assertions.assertAll;

      import {{project-path}}.feature.{{root-name}}.domain.model.dto.{{feature-name}};
      import java.util.Arrays;
      import java.util.List;
      import org.junit.jupiter.api.DisplayName;
      import org.junit.jupiter.api.Test;

      @DisplayName("[UNIT TEST] {{feature-as-text}} entity mapper")
      public class {{feature-name}}EntityMapperUT {

        @Test
        @DisplayName("[MUTATION] map input {{feature-as-text}} entity to {{feature-as-text}} dto")
        void expectReturnedMapped{{feature-name}}Dto() {
            {{feature-name}}Entity entity = {{feature-name}}Entity
              .builder()
              .id("id")
      {{test-data}}
              .build();

          {{feature-name}} dto = {{feature-name}}EntityMapper.toDto(entity);

          assertAll(
              () -> assertThat(dto.id()).isEqualTo(entity.getId()),
      {{entity-to-dto-assertions}}
          );
        }

        @Test
        @DisplayName("[MUTATION] map input {{feature-as-text}} entity collection to {{feature-as-text}} dto collection")
        void expectReturnedMapped{{feature-name}}DtoCollection() {
          {{feature-name}}Entity entityOne = {{feature-name}}Entity
              .builder()
              .id("idOne")
          {{test-data-one}}
              .build();
          {{feature-name}}Entity entityTwo = {{feature-name}}Entity
              .builder()
              .id("idTwo")
          {{test-data-two}}
              .build();
          List<{{feature-name}}Entity> entities = Arrays.asList(entityOne, entityTwo);

          List<{{feature-name}}> dtos = {{feature-name}}EntityMapper.toDtos(entities);

          assertAll(
              () -> assertThat(dtos).hasSize(2),
      {{entities-to-dtos-assertions}}
          );
        }

        @Test
        @DisplayName("[MUTATION] Map input {{feature-as-text}} dto to {{feature-as-text}} entity")
        void expectReturnedMapped{{feature-name}}Entity() {
          {{feature-name}} dto = {{feature-name}}
              .builder()
              .id("id")
      {{test-data}}
              .build();

          {{feature-name}}Entity entity = {{feature-name}}EntityMapper.toEntity(dto);

          assertAll(
              () -> assertThat(entity.getId()).isEqualTo(dto.id()),
      {{dto-to-entity-assertions}}
          );
        }

        @Test
        @DisplayName("[MUTATION] Map input {{feature-as-text}} dto collection to {{feature-as-text}} entity collection")
        void expectReturnedMapped{{feature-name}}EntityCollection() {
          {{feature-name}} dtoOne = {{feature-name}}
              .builder()
              .id("idOne")
          {{test-data-one}}
              .build();
          {{feature-name}} dtoTwo = {{feature-name}}
              .builder()
              .id("idTwo")
          {{test-data-two}}
              .build();
          List<{{feature-name}}> dtos = Arrays.asList(dtoOne, dtoTwo);

          List<{{feature-name}}Entity> entities = {{feature-name}}EntityMapper.toEntities(dtos);

          assertAll(
              () -> assertThat(entities).hasSize(2),
      {{dtos-to-entities-assertions}}
          );
        }
      }

      """;

  private final Config config;
  private final Domain domain;

  public EntityMapperUnitTestTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    String template = TEMPLATE;

    template = replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());
    template = replaceFeatureNameLowered(template, domain.feature());
    template = replaceFeatureAsText(template, domain.feature());
    template = replaceTestData(template, domain.data());
    template = replaceTestDataOne(template, domain.data());
    template = replaceTestDataTwo(template, domain.data());
    template = replaceDtoToEntityAssertions(template, domain.data());
    template = replaceEntityToDtoAssertions(template, domain.data());
    template = replaceEntitiesToDtosAssertions(template, domain.data());
    template = replaceDtosToEntitiesAssertions(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    return new File(
        Paths.STORAGE_PATH.get(
            config,
            domain.root(),
            domain.feature(),
            "EntityMapperUT",
            true));
  }

  private String replaceTestData(String template, List<String> data) {
    final String rowPattern = "      .%s(\"%s\")";
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
          .append(String.format(rowPattern, field, field))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{test-data}}", sb.toString());
  }

  private String replaceTestDataOne(String template, List<String> data) {
    final String rowPattern = "      .%s(\"%sOne\")";
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
          .append(String.format(rowPattern, field, field))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{test-data-one}}", sb.toString());
  }

  private String replaceTestDataTwo(String template, List<String> data) {
    final String rowPattern = "      .%s(\"%sTwo\")";
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
          .append(String.format(rowPattern, field, field))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{test-data-two}}", sb.toString());
  }

  private String replaceEntityToDtoAssertions(String template, List<String> data) {
    final String rowPattern = "        () -> assertThat(dto.%s()).isEqualTo(entity.get%s()),";
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
          .append(String.format(rowPattern, field, capitalize(field)))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 2);

    return template.replace("{{entity-to-dto-assertions}}", sb.toString());
  }

  private String replaceDtoToEntityAssertions(String template, List<String> data) {
    final String rowPattern = "        () -> assertThat(entity.get%s()).isEqualTo(dto.%s()),";
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
          .append(String.format(rowPattern, capitalize(field), field))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 2);

    return template.replace("{{dto-to-entity-assertions}}", sb.toString());
  }

  private String replaceEntitiesToDtosAssertions(String template, List<String> data) {
    final String rowPattern = "        () -> assertThat(dtos.get(%s).%s()).isEqualTo(entity%s.get%s()),";
    StringBuilder sb = new StringBuilder();

    sb.append(String.format(rowPattern, "0", "id", "One", "Id"));
    sb.append(System.lineSeparator());
    for (String field : data) {
      sb
          .append(String.format(rowPattern, "0", field, "One", capitalize(field)))
          .append(System.lineSeparator());
    }

    sb.append(String.format(rowPattern, "1", "id", "Two", "Id"));
    sb.append(System.lineSeparator());
    for (String field : data) {
      sb
          .append(String.format(rowPattern, "1", field, "Two", capitalize(field)))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 2);

    return template.replace("{{entities-to-dtos-assertions}}", sb.toString());
  }

  private String replaceDtosToEntitiesAssertions(String template, List<String> data) {
    final String rowPattern = "        () -> assertThat(entities.get(%s).get%s()).isEqualTo(dto%s.%s()),";
    StringBuilder sb = new StringBuilder();

    sb.append(String.format(rowPattern, "0", "Id", "One", "id"));
    sb.append(System.lineSeparator());
    for (String field : data) {
      sb
          .append(String.format(rowPattern, "0", capitalize(field), "One", field))
          .append(System.lineSeparator());
    }

    sb.append(String.format(rowPattern, "1", "Id", "Two", "id"));
    sb.append(System.lineSeparator());
    for (String field : data) {
      sb
          .append(String.format(rowPattern, "1", capitalize(field), "Two", field))
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 2);

    return template.replace("{{dtos-to-entities-assertions}}", sb.toString());
  }
}
