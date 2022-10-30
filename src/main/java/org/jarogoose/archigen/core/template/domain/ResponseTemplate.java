package org.jarogoose.archigen.core.template.domain;

import static org.springframework.util.StringUtils.capitalize;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class ResponseTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
      package {{project-path}}.feature.{{root-name}}.domain.model.response;

      import {{project-path}}.feature.{{root-name}}.domain.model.dto.{{feature-name}};
      import java.util.List;
      import lombok.Builder;

      @Builder
      public record ShowAll{{feature-name}}Response(
          List<{{feature-name}}> {{feature-name-lowercase}}
      ) {}

      """;

  private final Config config;
  private final Domain domain;

  public ResponseTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    String template = TEMPLATE;

    template = replaceAuthorName(template, config.author());
    template = replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());
    template = replaceFeatureNameLowered(template, domain.feature());

    return template;
  }

  @Override
  public File file() {
    final String featureName = String.format(
        "%s%s",
        "showAll",
        capitalize(domain.feature()));
    return new File(
        Paths.RESPONSE_PATH.get(
            config,
            domain.root(),
            featureName,
            "Response",
            false));
  }
}
