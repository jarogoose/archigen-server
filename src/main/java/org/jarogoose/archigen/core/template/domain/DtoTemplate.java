package org.jarogoose.archigen.core.template.domain;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class DtoTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
      package {{project-path}}.feature.{{root-name}}.domain.model.dto;

      import lombok.Builder;

      @Builder
      public record {{feature-name}}(
          String id,
      {{data}}
      ) {}

      """;

  private final Config config;
  private final Domain domain;

  public DtoTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    String template = TEMPLATE;

    template = replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());
    template = replaceDtoData(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    return new File(
        Paths.DTO_PATH.get(config, domain.root(), domain.feature(), "", false));
  }
}
