package org.jarogoose.archigen.core.template.testing;

import java.io.File;

import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public final class BehavioralTestTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.control;

  import static org.assertj.core.api.Assertions.assertThat;
  import static org.junit.jupiter.api.Assertions.assertAll;
  
  import com.fasterxml.jackson.core.JsonProcessingException;
  import com.fasterxml.jackson.databind.JsonMappingException;
  import com.fasterxml.jackson.databind.ObjectMapper;
  import com.jarogoose.enenbi.feature.{{root-name}}.domain.model.request.Add{{feature-name}}Request;
  import com.jarogoose.enenbi.feature.{{root-name}}.domain.model.response.ShowAll{{feature-name}}Response;
  import com.jarogoose.enenbi.feature.{{root-name}}.storage.{{feature-name}}StorageWrapper;
  import org.junit.jupiter.api.AfterAll;
  import org.junit.jupiter.api.BeforeAll;
  import org.junit.jupiter.api.DisplayName;
  import org.junit.jupiter.api.MethodOrderer;
  import org.junit.jupiter.api.Nested;
  import org.junit.jupiter.api.Order;
  import org.junit.jupiter.api.Test;
  import org.junit.jupiter.api.TestInstance;
  import org.junit.jupiter.api.TestInstance.Lifecycle;
  import org.junit.jupiter.api.TestMethodOrder;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.test.context.SpringBootTest;
  import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
  import org.springframework.boot.test.web.client.TestRestTemplate;
  import org.springframework.boot.test.web.server.LocalServerPort;
  import org.springframework.http.ResponseEntity;
  import org.springframework.test.context.ActiveProfiles;
  
  @ActiveProfiles("test")
  @DisplayName("[BEHAVIORAL TEST] {{feature-as-text}}")
  @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
  public class {{feature-name}}BT {
  
    @Autowired
    private TestRestTemplate rest;
  
    @Autowired
    private {{feature-name}}StorageWrapper {{feature-name-lowercase}}Storage;
  
    @LocalServerPort
    private int port;
  
    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("[SCENARIO] add and show all {{feature-as-text}}")
    class PreviewTemplates {
  
      @BeforeAll
      public void setUp() {
        {{feature-name-lowercase}}Storage.save{{feature-name}}();
      }
  
      @AfterAll
      public void cleanUp() {
        {{feature-name-lowercase}}Storage.clear();
      }
  
      @Test
      @Order(1)
      @DisplayName("[REQUEST] add {{feature-as-text}}")
      public void action_add{{feature-name}}()
          throws JsonMappingException, JsonProcessingException {
        var url = String.format(Given.ADD_{{feature-constant}}_URL, port);
        var request = new ObjectMapper()
            .readValue(Given.ADD_{{feature-constant}}_REQUEST, Add{{feature-name}}Request.class);
  
        var response = rest.postForEntity(
            url,
            request,
            Object.class
        );
  
        assertAll(
            () -> assertThat(response).isNotNull(),
            () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
            () -> assertThat(response.getBody()).isNull()
        );
      }
  
      @Test
      @Order(2)
      @DisplayName("[REQUEST] show all {{feature-as-text}}")
      public void action_showAll{{feature-name}}() {
        var url = String.format(Given.SHOW_ALL_{{feature-constant}}_URL, port);
  
        ResponseEntity<ShowAll{{feature-name}}Response> response = rest.getForEntity(
            url,
            ShowAll{{feature-name}}Response.class
        );
  
        assertAll(
            () -> assertThat(response).isNotNull(),
            () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
            () -> assertThat(response.getBody()).isNotNull(),
            () -> assertThat(response.getBody().{{feature-name-lowercase}}()).hasSize(1)
        );
      }
    }
  }
  
  """;

  private final Config config;
  private final Domain domain;

  public BehavioralTestTemplate(Config config, Domain domain) {
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
    template = replaceFeatureNameLowered(template, domain.feature());
    template = replaceFeatureAsText(template, domain.feature());
    template = replaceFeatureConstant(template, domain.feature());

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.CONTROLLER_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "BT",
        true
      )
    );
  }
}
