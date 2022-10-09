package org.jarogoose.archigen.core.template.api;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;

import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public final class FacadeActionTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.api;

  import {{project-path}}.feature.{{root-name}}.domain.exception.{{feature-name}}Exception;
  import org.springframework.stereotype.Service;

  /**
   * {@link {{feature-name}}ActionFacade} coordinates services calls needed for feature execution.
   * <p>
   * Action facade is responsible for:
   * <li> transactional execution of write-to-database calls </li>
   * <li> groups services dependencies </li>
   * <li> executes services calls in correct order </li>
   * <li> handles transactional calls </li>
   * <li> handles request <-> dto mapping </li>
   * <li> validates request if require </li>
   *
   * @author Generated by {{author-name}}
   */
  @Service
  public class {{feature-name}}ActionFacade {

    private final {{feature-name}}WriteService {{feature-name-lowercase}}WriteService;

    private {{feature-name}}ActionFacade({{feature-name}}WriteService {{feature-name-lowercase}}WriteService) {
      this.{{feature-name-lowercase}}WriteService = {{feature-name-lowercase}}WriteService;
    }

    public void add{{feature-name}}() {
      throw new {{feature-name}}Exception("Auto-generated method stub");
    }
  }

  """;

  private final Config config;
  private final Domain domain;
  
  public FacadeActionTemplate(Config config, Domain domain) {
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
      .get(config, domain.root(), domain.feature(), "ActionFacade"));
  }
}
