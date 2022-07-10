package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.DATA;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.jarogoose.archigen.util.StringUtils.splitByUpperCase;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.domain.Domain;

public class EntityTemplate {

  private static final String TEMPLATE = """
      package {{package}};
            
      import lombok.AllArgsConstructor;
      import lombok.Builder;
      import lombok.Data;
      import lombok.NoArgsConstructor;
      import org.springframework.data.mongodb.core.mapping.Document;
      import org.springframework.data.mongodb.core.mapping.Field;
      import org.springframework.data.mongodb.core.mapping.FieldType;
      import org.springframework.data.mongodb.core.mapping.MongoId;
            
      @Data
      @Builder
      @NoArgsConstructor
      @AllArgsConstructor
      @Document(collection = "{{document-name}}")
      class {{feature-name}}Entity {
      
      {{data-block}}
      }
      
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // package
    String packageName = String.format("%s.%s.%s",
        ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);

    // document name
    String documentName = formatDocumentName(splitByUpperCase(domain.feature()));
    template = template.replace("{{document-name}}", documentName);

    // data block
    StringBuilder dataBlock = new StringBuilder();
    for (int i = 0; i < domain.data().size(); i++) {
      String field = domain.data().get(i);
      String[] words = splitByUpperCase(field);

      if (field.equalsIgnoreCase("id")) {
        dataBlock.append("  @MongoId(FieldType.OBJECT_ID)").append(System.lineSeparator());
      }

      if (words.length > 1) {
        dataBlock.append(format("  @Field(\"%s\")", formatDocumentName(words)))
            .append(System.lineSeparator());
      }

      dataBlock.append(format("  private String %s;", field));

      if (i != domain.data().size() - 1) {
        dataBlock.append(System.lineSeparator());
      }
    }
    template = template.replace(DATA.toString(), dataBlock.toString());

    return template;
  }

  private String formatDocumentName(String[] words) {
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word.toLowerCase()).append("_");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }
}
