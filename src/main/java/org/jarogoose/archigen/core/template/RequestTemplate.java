package org.jarogoose.archigen.core.template;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;
import java.util.List;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.domain.Config;

public class RequestTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
    package {{project-path}}.feature.{{root-name}}.domain.model.request;

    import com.fasterxml.jackson.annotation.JsonProperty;
    
    import lombok.Builder;
    
    @Builder
    public record Add{{root-name}}Request(
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
    template = template.replace("{{data}}", formatData(domain.data()));

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.REQUEST_PATH
      .get(config, domain.root(), domain.feature(), "Request"));
  }

  private String formatData(List<String> data) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    @JsonProperty(\"")
        .append(field)
        .append("\") String ")
        .append(field)
        .append(",")
        .append(System.lineSeparator());
    }
    return sb.toString();
  }
}
