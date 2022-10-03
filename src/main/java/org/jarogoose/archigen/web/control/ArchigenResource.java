package org.jarogoose.archigen.web.control;

import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.web.api.ArchigenService;
import org.jarogoose.archigen.web.domain.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("archigen")
@CrossOrigin(origins = "http://localhost:4200")
public class ArchigenResource {

  private final ArchigenService service;

  public ArchigenResource(ArchigenService service) {
    this.service = service;
  }

  @PostMapping("generate")
  public ResponseEntity<Object> generate(@RequestBody Domain request) {
    try {
      service.generateAll(request);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("load-configs")
  public ResponseEntity<Object> loadConfigs() {
    try {
      var response = service.loadConfig();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("save-configs")
  public ResponseEntity<Object> changeConfigs(@RequestBody Config config) {
    try {
      service.saveConfig(config);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
