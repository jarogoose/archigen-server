package org.jarogoose.archigen.web.config.control;

import lombok.extern.slf4j.Slf4j;
import org.jarogoose.archigen.web.config.api.ConfigFacade;
import org.jarogoose.archigen.web.config.domain.model.request.ModifyConfigRequest;
import org.jarogoose.archigen.web.config.domain.model.request.SaveConfigRequest;
import org.jarogoose.archigen.web.config.domain.model.response.LoadAllConfigsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("archigen-ui/configs-api")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigsController {

  private final ConfigFacade facade;

  public ConfigsController(ConfigFacade facade) {
    this.facade = facade;
  }

  @GetMapping("load-all-configs")
  public ResponseEntity<LoadAllConfigsResponse> loadAllConfigs() {
    try {
      var response = facade.loadAllConfigs();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("[CONFIGS] something went wrong", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("load-project-configs/{name}")
  public ResponseEntity<Object> loadProjectConfigs(
      @PathVariable("name") String projectName) {
    try {
      var response = facade.loadConfig(projectName);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("[CONFIGS] something went wrong", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("save-configs")
  public ResponseEntity<Object> saveConfigs(
      @RequestBody SaveConfigRequest request) {
    try {
      facade.saveConfig(request);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("[CONFIGS] something went wrong", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping("modify-configs")
  public ResponseEntity<Object> modifyConfigs(
      @RequestBody ModifyConfigRequest request) {
    try {
      facade.modifyConfig(request);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.error("[CONFIGS] something went wrong", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
