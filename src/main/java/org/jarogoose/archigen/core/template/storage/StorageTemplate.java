package org.jarogoose.archigen.core.template.storage;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

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
    String template = TEMPLATE;

    template =
      replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.STORAGE_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "Storage",
        false
      )
    );
  }
}
