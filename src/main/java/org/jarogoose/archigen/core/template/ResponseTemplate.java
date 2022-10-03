package org.jarogoose.archigen.core.template;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.domain.Config;

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
    final String projectPath = String.format(
      "%s.%s",
      config.artefact(),
      config.project()
    );
    final String featureName = capitalize(domain.feature());
    final String featureNameLowercase = domain.feature();

    String template = TEMPLATE;

    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{feature-name}}", featureName);
    template = template.replace("{{feature-name-lowercase}}", featureNameLowercase);

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.RESPONSE_PATH
      .get(config, domain.root(), domain.feature(), "Response"));
  }
}
