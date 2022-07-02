package org.jarogoose.archigen.web.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.jarogoose.archigen.ArchigenApplication;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.web.api.ArchigenService;
import org.jarogoose.archigen.web.domain.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("archigen")
@CrossOrigin(origins = "http://localhost:4200")
public class ArchigenResource {

  public static final Map<String, String> PROPERTIES = new HashMap<>();

  private final ArchigenService service;

  public ArchigenResource(ArchigenService service) {
    this.service = service;
    try {
      this.initProperties();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
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

  @GetMapping("configs")
  public ResponseEntity<Object> loadConfigs() {
    try {
      var response = service.loadConfig();
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PostMapping("configs")
  public ResponseEntity<Object> changeConfigs(@RequestBody Config config) {
    try {
      service.saveConfig(config);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private void initProperties() throws IOException {
    InputStream input = ArchigenApplication.class.getClassLoader()
        .getResourceAsStream("application.yml");

    if (input == null) {
      log.error("Properties file was not found");
      return;
    }

    Properties prop = new Properties();
    prop.load(input);

    for (final String name : prop.stringPropertyNames()) {
      PROPERTIES.put(name, prop.getProperty(name));
    }
    PROPERTIES.put("user.home", System.getProperty("user.home"));
    log.info("Properties loaded");
  }
}
