package org.jarogoose.archigen.web.generate.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jarogoose.archigen.web.config.storage.ConfigStorageWrapper;
import org.jarogoose.archigen.web.generate.domain.model.request.GenerateRequest;
import org.jarogoose.archigen.web.generate.domain.model.response.PreviewResponse;
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
@DisplayName("[BEHAVIORAL TEST] Generate")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GenerateBT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ConfigStorageWrapper configStorage;

  @LocalServerPort
  private int port;

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  @DisplayName("[SCENARIO] success pass for preview templates")
  class PreviewTemplates {

    @BeforeAll
    public void setUp() {
      configStorage.save(Given.SAVE_CONFIG_ENTITY);
    }

    @AfterAll
    public void cleanUp() {
      configStorage.clear();
    }

    @Test
    @Order(1)
    @DisplayName("[REQUEST] preview of all templates")
    public void action_saveFirstConfigs()
      throws JsonMappingException, JsonProcessingException {
      var url = String.format(Given.PREVIEW_ALL_URL, port);
      var request = new ObjectMapper()
      .readValue(Given.GENERATE_REQUEST, GenerateRequest.class);

      var response = (ResponseEntity<PreviewResponse>) restTemplate.postForEntity(
        url,
        request,
        PreviewResponse.class
      );

      assertAll(
        () -> assertThat(response).isNotNull(),
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(response.getBody()).isNotNull(),
        () -> assertThat(response.getBody().templates()).hasSize(19)
      );
    }
  }
}
