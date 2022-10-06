package org.jarogoose.archigen.web.generate.api;

import java.io.IOException;
import java.util.List;
import org.jarogoose.archigen.core.ArchigenGenerator;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.api.ConfigsService;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.mapper.GenerateMapper;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.jarogoose.archigen.web.generate.domain.model.request.GenerateRequest;
import org.springframework.stereotype.Service;

@Service
public class GenerateFacade {

  private ArchigenGenerator generator;
  private ConfigsService configsService;

  public GenerateFacade(
    ArchigenGenerator generator,
    ConfigsService configsService
  ) {
    this.generator = generator;
    this.configsService = configsService;
  }

  public void generateAll(GenerateRequest request) throws IOException {
    Config config = configsService.loadConfig(request.projectName());
    Domain domain = GenerateMapper.toDomainDto(request);
    List<ArcTemplate> templates = generator.prepare(config, domain);
    generator.generate(templates);
  }

  public List<String> preview(GenerateRequest request) {
    Config config = configsService.loadConfig(request.projectName());
    Domain domain = GenerateMapper.toDomainDto(request);
    List<ArcTemplate> templates = generator.prepare(config, domain);
    return generator.preview(templates);
  }
}
