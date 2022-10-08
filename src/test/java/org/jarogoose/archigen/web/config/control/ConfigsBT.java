package org.jarogoose.archigen.web.config.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jarogoose.archigen.web.config.domain.model.request.SaveConfigRequest;
import org.jarogoose.archigen.web.config.domain.model.response.LoadAllConfigsResponse;
import org.jarogoose.archigen.web.config.storage.ConfigStorageWrapper;
import org.junit.jupiter.api.AfterAll;
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
@DisplayName("[BEHAVIORAL TEST] Configs")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ConfigsBT {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ConfigStorageWrapper storage;

  @LocalServerPort
  private int port;

  @Nested
  @TestInstance(Lifecycle.PER_CLASS)
  @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
  @DisplayName("[SCENARIO] success pass for save and load configs")
  class SaveLoadConfigs {

    @AfterAll
    public void cleanUp() {
      storage.clear();
    }

    @Test
    @Order(1)
    @DisplayName("[REQUEST] save first configs")
    public void action_saveFirstConfigs()
      throws JsonMappingException, JsonProcessingException {
      var url = String.format(Given.SAVE_CONFIG_URL, port);
      var request = new ObjectMapper()
      .readValue(Given.SAVE_CONFIG_JSON, SaveConfigRequest.class);

      var response = (ResponseEntity<Object>) restTemplate.postForEntity(
        url,
        request,
        Object.class
      );

      assertAll(
        () -> assertThat(response).isNotNull(),
        () -> assertThat(response.getBody()).isNull(),
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200)
      );
    }

    @Test
    @Order(2)
    @DisplayName("[REQUEST] load all configs")
    public void action_loadAllConfigs() {
      var url = String.format(Given.GET_ALL_CONFIG_URL, port);

      ResponseEntity<LoadAllConfigsResponse> response = restTemplate.getForEntity(
        url,
        LoadAllConfigsResponse.class
      );

      assertAll(
        () -> assertThat(response).isNotNull(),
        () -> assertThat(response.getStatusCodeValue()).isEqualTo(200),
        () -> assertThat(response.getBody()).isNotNull(),
        () -> assertThat(response.getBody().configs()).hasSize(1)
      );
    }
  }
}
