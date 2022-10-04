package org.jarogoose.archigen.core.template;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.domain.Config;

public class StorageTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.storage;

  import org.springframework.data.mongodb.repository.MongoRepository;
  import org.springframework.stereotype.Repository;
  
  @Repository
  interface {{feature-name}}Storage extends MongoRepository<{{feature-name}}Entity, String> {}
  
  """;

  private final Config config;
  private final Domain domain;
  
  public StorageTemplate(Config config, Domain domain) {
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
    return new File(Paths.STORAGE_PATH
      .get(config, domain.root(), domain.feature(), "Storage"));
  }
}
