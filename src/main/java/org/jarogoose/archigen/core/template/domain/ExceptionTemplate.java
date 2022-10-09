package org.jarogoose.archigen.core.template.domain;

import static org.springframework.util.StringUtils.capitalize;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class ExceptionTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.domain.exception;

  public class {{feature-name}}Exception extends RuntimeException {

    public {{feature-name}}Exception(String message) {
      super(message);
    }

    public {{feature-name}}Exception(String message, Throwable cause) {
      super(message, cause);
    }
  }

  """;

  private final Config config;
  private final Domain domain;

  public ExceptionTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    final String projectPath = String.format(
      "%s.%s",
      config.artefact(),
      config.project()
    );
    final String featureName = capitalize(domain.feature());

    String template = TEMPLATE;

    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{feature-name}}", featureName);

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.EXCEPTION_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "Exception",
        false
      )
    );
  }
}
