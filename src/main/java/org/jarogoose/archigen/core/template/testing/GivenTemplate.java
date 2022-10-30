package org.jarogoose.archigen.core.template.testing;

import java.io.File;
import java.util.List;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class GivenTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
      package {{project-path}}.feature.{{root-name}}.control;

      interface Given {

        String ADD_{{feature-constant}}_URL = {{post-test-url}};

        String SHOW_ALL_{{feature-constant}}_URL = {{get-test-url}};

        String ADD_{{feature-constant}}_REQUEST = {{triple-quotes}}
            {
      {{post-json-data}}
            }
            {{triple-quotes}};
      }

      """;

  private final Config config;
  private final Domain domain;

  public GivenTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    String template = TEMPLATE;

    template = replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = template.replace("{{triple-quotes}}", "\"\"\"");
    template = template.replace("{{post-test-url}}", buildPostUrl(domain));
    template = template.replace("{{get-test-url}}", buildGetUrl(domain));
    template = replaceUri(template, domain.feature());
    template = template.replace("{{post-json-data}}", buildPostJson(domain.data()));
    template = replaceFeatureConstant(template, domain.feature());

    return template;
  }

  @Override
  public File file() {
    return new File(
        Paths.CONTROLLER_PATH.get(
            config,
            domain.root(),
            "Given",
            "",
            true));
  }

  private String buildPostUrl(Domain domain) {
    return new StringBuilder()
        .append("\"")
        .append("http://localhost:%s/")
        .append(domain.restApi())
        .append("/")
        .append(domain.root())
        .append("-api/action/add-")
        .append("{{uri}}")
        .append("\"")
        .toString();
  }

  private String buildGetUrl(Domain domain) {
    return new StringBuilder()
        .append("\"")
        .append("http://localhost:%s/")
        .append(domain.restApi())
        .append("/")
        .append(domain.root())
        .append("-api/summary/show-all-")
        .append("{{uri}}")
        .append("\"")
        .toString();
  }

  private String buildPostJson(List<String> data) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
          .append("          \"")
          .append(field)
          .append("\"")
          .append(": \"")
          .append(field)
          .append("\",")
          .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return sb.toString();
  }
}
