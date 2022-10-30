package org.jarogoose.archigen.core.template.domain;

import static org.springframework.util.StringUtils.capitalize;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class RequestTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
      package {{project-path}}.feature.{{root-name}}.domain.model.request;

      import com.fasterxml.jackson.annotation.JsonProperty;

      import lombok.Builder;

      @Builder
      public record Add{{feature-name}}Request(
      {{data}}
      ) {}

      """;

  private final Config config;
  private final Domain domain;

  public RequestTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    String template = TEMPLATE;

    template = replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());
    template = replaceRequestData(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    final String featureName = String.format(
        "%s%s",
        "add",
        capitalize(domain.feature()));
    return new File(
        Paths.REQUEST_PATH.get(
            config,
            domain.root(),
            featureName,
            "Request",
            false));
  }
}
