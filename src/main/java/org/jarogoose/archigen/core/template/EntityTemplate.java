package org.jarogoose.archigen.core.template;

import static org.jarogoose.archigen.core.util.StringUtils.splitByUpperCase;
import static org.springframework.util.StringUtils.capitalize;
import java.io.File;
import java.util.List;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.domain.Config;

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
    final String projectPath = String.format(
      "%s.%s",
      config.artefact(),
      config.project()
    );
    final String featureName = capitalize(domain.feature());
    final String documentName = formatDocumentName(domain.feature());

    String template = TEMPLATE;

    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{feature-name}}", featureName);
    template = template.replace("{{document-name}}", documentName);
    template = template.replace("{{data}}", formatData(domain.data()));

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.STORAGE_PATH
      .get(config, domain.root(), domain.feature(), "Entity"));
  }

  private String formatData(List<String> data) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("private String ")
        .append(field.toLowerCase())
        .append(";")
        .append(System.lineSeparator());
    }
    return sb.toString();
  }

  private String formatDocumentName(String feature) {
    StringBuilder sb = new StringBuilder();
    for (String word : splitByUpperCase(feature)) {
      sb.append(word.toLowerCase()).append("_");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString().toUpperCase();
  }
}
