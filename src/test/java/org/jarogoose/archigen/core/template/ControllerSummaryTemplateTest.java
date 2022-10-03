package org.jarogoose.archigen.core.template;

import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.template.gwt.Then;
import org.jarogoose.archigen.web.domain.Config;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ControllerSummaryTemplateTest {

    private static final String EXPECTED = """
    """;

    @Test
    @DisplayName("[FEATURE] Summary controller class generated and formatted as expected")
    public void generateSummaryControllerClass() {
        Domain domain = Domain.builder()
                .feature("foodItem")
                .root("food").restApi("user-ui")
                .readWrite("RW")
                .build();

        Config config = Config.builder()
                .artefact("com.jarogoose")
                .project("enenbi")
                .baseDir("")
                .author("Yaroslav Kynyk")
                .build();

        ArcTemplate template = new ControllerSummaryTemplate(config, domain);
        String actual = template.content();

        Then.validTemplate(actual, EXPECTED);
    }
}
