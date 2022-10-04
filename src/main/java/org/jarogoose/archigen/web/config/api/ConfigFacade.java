package org.jarogoose.archigen.web.config.api;

import java.util.List;
import org.jarogoose.archigen.web.config.domain.mapper.ConfigMapper;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.config.domain.model.request.ModifyConfigRequest;
import org.jarogoose.archigen.web.config.domain.model.request.SaveConfigRequest;
import org.springframework.stereotype.Service;

@Service
public class ConfigFacade {

  private final ConfigsService service;

  public ConfigFacade(ConfigsService service) {
    this.service = service;
  }

  public List<Config> loadAllConfigs() {
    return service.loadAllConfigs();
  }

  public Config loadConfig(final String projectName) {
    return service.loadConfig(projectName);
  }

  public void saveConfig(SaveConfigRequest request) {
    service.saveConfig(ConfigMapper.toDto(request));
  }

  public void modifyConfig(ModifyConfigRequest request) {
    service.modifyConfig(ConfigMapper.toDto(request));
  }
}
