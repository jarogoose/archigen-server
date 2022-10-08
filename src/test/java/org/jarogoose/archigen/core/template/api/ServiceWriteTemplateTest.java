package org.jarogoose.archigen.core.template.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.template.api.ServiceWriteTemplate;
import org.jarogoose.archigen.core.template.gwt.Then;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Service write template generation")
class ServiceWriteTemplateTest {

  private static final String EXPECTED = """
    """;

  @Test
  @DisplayName("[FEATURE] Service write class generated and formatted as expected")
  public void generateServiceClass() {
    Domain domain = Domain
      .builder()
      .feature("foodItem")
      .root("food")
      .restApi("user-ui")
      .readWrite("RW")
      .build();

    Config config = Config
      .builder()
      .artefact("com.jarogoose")
      .project("enenbi")
      .baseDir("")
      .author("Yaroslav Kynyk")
      .build();

    ArcTemplate template = new ServiceWriteTemplate(config, domain);
    String actual = template.content();

    System.out.println(actual);
    assertNotNull(actual);

    // Then.validTemplate(actual, EXPECTED);
  }
}
