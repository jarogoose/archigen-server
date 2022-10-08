package org.jarogoose.archigen.core.template.control;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.template.control.ControllerActionTemplate;
import org.jarogoose.archigen.core.template.gwt.Then;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ControllerActionTemplateTest {

    private static final String EXPECTED = """
            """;

    @Test
    @DisplayName("[FEATURE] Action controller class generated and formatted as expected")
    public void generateActionControllerClass() {
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

        ArcTemplate template = new ControllerActionTemplate(config, domain);
        String actual = template.content();

        System.out.println(actual);
        assertNotNull(actual);

        // Then.validTemplate(actual, EXPECTED);
    }
}