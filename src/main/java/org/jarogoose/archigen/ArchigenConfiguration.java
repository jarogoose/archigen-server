package org.jarogoose.archigen;

import javax.annotation.PostConstruct;

import org.jarogoose.archigen.web.config.storage.ConfigEntity;
import org.jarogoose.archigen.web.config.storage.ConfigStorage;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ArchigenConfiguration implements WebMvcConfigurer {

  private final ConfigStorage storage;

  public ArchigenConfiguration(ConfigStorage storage) {
    this.storage = storage;
  }

  @PostConstruct
  public void initDefaultConfigs() {
    var configs = storage.findAll();

    if (configs.isEmpty()) {
      var config = ConfigEntity.builder()
          .artefact("com.jarogoose")
          .project("enenbi")
          .baseDir("Dev/tmp")
          .build();
      storage.save(config);
    }
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedMethods("*");
  }
}
