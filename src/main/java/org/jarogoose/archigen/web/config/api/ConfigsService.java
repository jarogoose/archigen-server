package org.jarogoose.archigen.web.config.api;

import java.util.List;
import org.jarogoose.archigen.web.config.domain.mapper.ConfigMapper;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.config.storage.ConfigEntity;
import org.jarogoose.archigen.web.config.storage.ConfigStorage;
import org.springframework.stereotype.Service;

@Service
public class ConfigsService {

  private ConfigStorage storage;

  public ConfigsService(ConfigStorage storage) {
    this.storage = storage;
  }

  public Config loadConfig(String projectName) {
    ConfigEntity entity = storage.findByProjectName(projectName).get();
    return ConfigMapper.toDto(entity);
  }

  public List<Config> loadAllConfigs() {
    return ConfigMapper.toDtos(storage.findAll());
  }

  public void saveConfig(Config dto) {
    storage.save(ConfigMapper.toEntity(dto));
  }

  public void modifyConfig(Config dto) {
    storage.save(ConfigMapper.toEntity(dto));
  }
}
