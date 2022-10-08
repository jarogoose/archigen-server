package org.jarogoose.archigen.core.template.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.template.domain.DtoMapperTemplate;
import org.jarogoose.archigen.core.template.gwt.Then;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Dto mapper template generation")
class DtoMapperTemplateTest {

  private static final String EXPECTED = """
      """;

  @Test
  @DisplayName("[FEATURE] Dto mapper class generated and formatted as expected")
  public void generateDtoMapperClass() {
    Domain domain = Domain.builder()
    .feature("foodItem")
    .root("food").restApi("user-ui")
    .readWrite("RW")
    .data(List.of("name", "category", "quantity"))
    .build();

    Config config = Config.builder()
    .artefact("com.jarogoose")
    .project("enenbi")
    .baseDir("")
    .author("Yaroslav Kynyk")
    .build();

    ArcTemplate template = new DtoMapperTemplate(config, domain);
    String actual = template.content();

    System.out.println(actual);
    assertNotNull(actual);

    // Then.validTemplate(actual, EXPECTED);
  }
}
