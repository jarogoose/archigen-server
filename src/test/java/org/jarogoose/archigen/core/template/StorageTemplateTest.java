package org.jarogoose.archigen.core.template;

import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.template.gwt.Then;
import org.jarogoose.archigen.web.domain.Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Storage template generation")
class StorageTemplateTest {

  private static final String EXPECTED = """
      """;

  @Test
  @DisplayName("[FEATURE] Storage class generated and formatted as expected")
  public void generateStorageClass() {
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

    ArcTemplate template = new StorageTemplate(config, domain);
    String actual = template.content();

    Then.validTemplate(actual, EXPECTED);
  }
}
