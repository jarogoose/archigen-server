package org.jarogoose.archigen.core.template.storage;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public class EntityTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
  package {{project-path}}.feature.{{root-name}}.storage;

  import lombok.AllArgsConstructor;
  import lombok.Builder;
  import lombok.Data;
  import lombok.NoArgsConstructor;
  import org.springframework.data.mongodb.core.mapping.Document;
  import org.springframework.data.mongodb.core.mapping.FieldType;
  import org.springframework.data.mongodb.core.mapping.MongoId;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Document(collection = "{{document-name}}")
  class {{feature-name}}Entity {

    @MongoId(FieldType.OBJECT_ID)
    private String id;
    {{data}}
  }

  """;

  private final Config config;
  private final Domain domain;

  public EntityTemplate(Config config, Domain domain) {
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
    template = replaceFeatureName(template, domain.feature());
    template = replaceDocumentName(template, domain.feature());
    template = replaceEntityData(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.STORAGE_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "Entity",
        false
      )
    );
  }
}
