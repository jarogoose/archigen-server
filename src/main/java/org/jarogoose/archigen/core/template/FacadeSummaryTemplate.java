package org.jarogoose.archigen.core.template;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;

import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public final class FacadeSummaryTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.api;

  import {{project-path}}.feature.{{root-name}}.domain.exception.{{feature-name}}Exception;
  import org.springframework.stereotype.Service;

  /**
   * {@link {{feature-name}}SummaryFacade} coordinates services calls needed for feature execution.
   * <p>
   * Summary facade is responsible for:
   * <li> execution of read-from-resource calls </li>
   * <li> groups services dependencies </li>
   * <li> executes services calls in correct order </li>
   * <li> handles transactional calls </li>
   * <li> handles request <-> dto mapping </li>
   * <li> validates request if require </li>
   *
   * @author Generated by {{author-name}}
   */
  @Service
  public class {{feature-name}}SummaryFacade {

    private {{feature-name}}ReadService {{feature-name-lowercase}}ReadService;

    private {{feature-name}}SummaryFacade({{feature-name}}ReadService {{feature-name-lowercase}}WriteService) {
      this.{{feature-name-lowercase}}ReadService = {{feature-name-lowercase}}ReadService;
    }

    public void add{{feature-name}}() {
      throw new {{feature-name}}Exception("Auto-generated method stub");
    }
  }
  
  """;

  private final Config config;
  private final Domain domain;
  
  public FacadeSummaryTemplate(Config config, Domain domain) {
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

    template = template.replace("{{author-name}}", config.author());
    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{feature-name}}", featureName);
    template = template.replace("{{feature-name-lowercase}}", featureNameLowercase);

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.API_PATH
      .get(config, domain.root(), domain.feature(), "SummaryFacade"));
  }
}
