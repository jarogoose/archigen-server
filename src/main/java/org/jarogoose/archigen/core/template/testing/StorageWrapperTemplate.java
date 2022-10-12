package org.jarogoose.archigen.core.template.testing;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public final class StorageWrapperTemplate implements ArcTemplate {

  public static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.storage;

  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.stereotype.Component;
  import org.springframework.test.context.ActiveProfiles;

  @Component
  @ActiveProfiles("test")
  public class {{feature-name}}StorageWrapper {

    @Autowired
    private {{feature-name}}Storage storage;

    public void clear() {
      storage.deleteAll();
    }

    public void save{{feature-name}}() {
      {{feature-name}}Entity entity = {{feature-name}}Entity.builder()
    {{test-entity-data}}
          .build();
      storage.save(entity);
    }
  }
  """;

  private final Config config;
  private final Domain domain;

  public StorageWrapperTemplate(Config config, Domain domain) {
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
    template = replaceTestEntityData(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.STORAGE_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "StorageWrapper",
        true
      )
    );
  }
}
