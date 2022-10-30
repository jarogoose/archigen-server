package org.jarogoose.archigen.core.template.api;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public final class ServiceReadTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
      package {{project-path}}.feature.{{root-name}}.api;

      import {{project-path}}.feature.{{root-name}}.domain.exception.{{feature-name}}Exception;
      import {{project-path}}.feature.{{root-name}}.storage.{{feature-name}}Loader;
      import org.springframework.stereotype.Service;

      /**
       * {@link {{feature-name}}ReadService} executes feature business logic.
       * <p>
       * Read service is responsible for:
       * <li> executes algorithms for read operations </li>
       * <li> every method must return response object or collection of responses objects </li>
       * <li> groups and executes loader calls in order </li>
       * <li> logs business logic execution </li>
       *
       * @author Generated by {{author-name}}
       */
      @Service
      class {{feature-name}}ReadService {

        private final {{feature-name}}Loader {{feature-name-lowercase}}Loader;

        private {{feature-name}}ReadService({{feature-name}}Loader {{feature-name-lowercase}}Loader) {
          this.{{feature-name-lowercase}}Loader = {{feature-name-lowercase}}Loader;
        }

        public void showAll{{feature-name}}() {
          throw new {{feature-name}}Exception("Auto-generated method stub");
        }
      }

      """;

  private final Config config;
  private final Domain domain;

  public ServiceReadTemplate(Config config, Domain domain) {
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
    return new File(
        Paths.API_PATH.get(
            config,
            domain.root(),
            domain.feature(),
            "ReadService",
            false));
  }
}
