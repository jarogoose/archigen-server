package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;

public class EntityTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/storage/entity-pojo.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // package
    String packageName = String.format("%s.%s.%s",
        ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);
    template = template.replace("{{package}}", packageName);

    // class name
    String className = format("%s", capitalize(domain.feature()));
    template = template.replace("{{class-name}}", className);

    // document name
    String documentName = formatDocumentName(domain.feature().split("(?=\\p{Upper})"));
    template = template.replace("{{document-name}}", documentName);

    // data block
    StringBuilder dataBlock = new StringBuilder();
    for (int i = 0; i < domain.data().size(); i++) {
      String field = domain.data().get(i);
      String[] words = field.split("(?=\\p{Upper})");

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
    template = template.replace("{{data-block}}", dataBlock.toString());

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
