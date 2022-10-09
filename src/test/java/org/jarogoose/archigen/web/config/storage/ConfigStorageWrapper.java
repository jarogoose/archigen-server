package org.jarogoose.archigen.web.config.storage;

import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@Component
@ActiveProfiles("test")
public class ConfigStorageWrapper {

  private final ConfigStorage storage;

  public ConfigStorageWrapper(ConfigStorage storage) {
    this.storage = storage;
  }

  public void clear() {
    storage.deleteAll();
  }

  public void save(ConfigEntity config) {
    storage.save(config);
  }
}
