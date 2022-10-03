package org.jarogoose.archigen.web.api;

import java.io.IOException;
import java.util.List;
import org.jarogoose.archigen.core.ArchigenGenerator;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.domain.Config;
import org.jarogoose.archigen.web.domain.Mapper;
import org.jarogoose.archigen.web.storage.ConfigStorage;
import org.springframework.stereotype.Service;

@Service
public class ArchigenService {

  private final ArchigenGenerator generator;
  private final ConfigStorage configStorage;

  public ArchigenService(ArchigenGenerator generator, ConfigStorage configStorage) {
    this.generator = generator;
    this.configStorage = configStorage;
  }

  public void generateAll(Domain domain) throws IOException {
    Config config = loadConfig();
    List<ArcTemplate> templates = generator.prepare(config, domain);
    generator.generate(templates);
  }

  public void saveConfig(Config config) {
    configStorage.save(Mapper.toEntity(config));
  }

  public Config loadConfig() {
    var config = configStorage.findAll().get(0);
    return Mapper.toDto(config);
  }
}
