package org.jarogoose.archigen;

import javax.annotation.PostConstruct;
import org.jarogoose.archigen.web.storage.ConfigEntity;
import org.jarogoose.archigen.web.storage.ConfigStorage;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArchigenConfiguration {

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

}
