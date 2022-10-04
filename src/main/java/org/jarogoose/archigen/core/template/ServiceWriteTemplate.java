package org.jarogoose.archigen.core.template;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;

import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public final class ServiceWriteTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.api;

  import {{project-path}}.feature.{{root-name}}.domain.exception.{{feature-name}}Exception;
  import {{project-path}}.feature.{{root-name}}.storage.{{feature-name}}Loader;
  import org.springframework.stereotype.Service;

  /**
   * {@link {{feature-name}}WriteService} executes feature business logic.
   * <p>
   * Write service is responsible for:
   * <li> executes algorithms for write operations </li>
   * <li> all methods are commands and does not return anything </li>
   * <li> groups and executes loader calls in order </li>
   * <li> logs business logic execution </li>
   *
   * @author Generated by {{author-name}}
   */
  @Service
  class {{feature-name}}WriteService {
  
    private {{feature-name}}Loader {{feature-name}}Loader;
  
    private {{feature-name}}WriteService({{feature-name}}Loader {{feature-name}}Loader) {
      this.{{feature-name}}Loader = {{feature-name}}Loader;
    }
  
    public void add{{feature-name}}() {
      throw new {{feature-name}}Exception("Auto-generated method stub");
    }
  }
  
  """;

  private final Config config;
  private final Domain domain;
  
  public ServiceWriteTemplate(Config config, Domain domain) {
    this.config = config;
    this.domain = domain;
  }

  @Override
  public String content() {
    final String projectPath = String.format("%s.%s", config.artefact(), config.project());
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
      .get(config, domain.root(), domain.feature(), "WriteService"));
  }
}
