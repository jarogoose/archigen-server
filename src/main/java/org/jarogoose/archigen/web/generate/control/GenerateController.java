package org.jarogoose.archigen.web.generate.control;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

import org.jarogoose.archigen.web.generate.api.GenerateFacade;
import org.jarogoose.archigen.web.generate.domain.model.request.GenerateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("archigen-ui/generate-api")
@CrossOrigin(origins = "http://localhost:4200")
public class GenerateController {

  private GenerateFacade facade;

  public GenerateController(GenerateFacade facade) {
    this.facade = facade;
  }

  @PostMapping("generate-all")
  public ResponseEntity<Object> generate(@RequestBody GenerateRequest request) {
    try {
      facade.generateAll(request);
      return ResponseEntity.ok().build();
    } catch (IOException e) {
      log.error("[GENERATE] failed to generate file", e);
      return ResponseEntity.internalServerError().build();
    } catch (Exception e) {
      log.error("[GENERATE] something went wrong", e);
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("preview")
  public ResponseEntity<Object> loadConfigs(
    @RequestBody GenerateRequest request
  ) {
    try {
      var response = facade.preview(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error("[GENERATE] something went wrong", e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
