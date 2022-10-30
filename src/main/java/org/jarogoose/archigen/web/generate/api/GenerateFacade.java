package org.jarogoose.archigen.web.generate.api;

import java.io.IOException;
import java.util.List;
import org.jarogoose.archigen.core.ArchigenGenerator;
import org.jarogoose.archigen.core.TemplateKey;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.api.ConfigsService;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.mapper.GenerateMapper;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.jarogoose.archigen.web.generate.domain.model.dto.TemplateFilter;
import org.jarogoose.archigen.web.generate.domain.model.request.GenerateRequest;
import org.jarogoose.archigen.web.generate.domain.model.response.PreviewResponse;
import org.springframework.stereotype.Service;

@Service
public class GenerateFacade {

  private final ArchigenGenerator generator;
  private final ConfigsService configsService;

  public GenerateFacade(
      final ArchigenGenerator generator,
      final ConfigsService configsService) {
    this.generator = generator;
    this.configsService = configsService;
  }

  public void generateAll(final GenerateRequest request) throws IOException {
    final Config config = configsService.loadConfig(request.projectName());
    final Domain domain = GenerateMapper.toDomainDto(request);
    final List<ArcTemplate> templates = generator.prepare(
        config,
        domain,
        new TemplateFilter(TemplateKey.all()));
    generator.generate(templates);
  }

  public PreviewResponse previewAll(final GenerateRequest request) {
    final Config config = configsService.loadConfig(request.projectName());
    final Domain domain = GenerateMapper.toDomainDto(request);
    final List<ArcTemplate> templates = generator.prepare(
        config,
        domain,
        new TemplateFilter(TemplateKey.all()));
    final List<String> previewTemplates = generator.preview(templates);
    return PreviewResponse.builder().templates(previewTemplates).build();
  }
}
