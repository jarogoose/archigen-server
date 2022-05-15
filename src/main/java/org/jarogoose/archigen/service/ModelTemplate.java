package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.DTO_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;

public class ModelTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/domain/model-pojo.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // package
    String packageName = String.format("%s.%s.%s",
        ROOT_PACKAGE, domain.root(), DTO_PACKAGE);
    template = template.replace("{{package}}", packageName);

    // class name
    String className = format("%s", capitalize(domain.feature()));
    template = template.replace("{{class-name}}", className);

    // data block
    StringBuilder dataBlock = new StringBuilder();
    for (int i = 0; i < domain.data().size(); i++) {
      dataBlock.append(format("  private final String %s;",
          domain.data().get(i)));
      if (i != domain.data().size() - 1) {
        dataBlock.append(System.lineSeparator());
      }
    }
    template = template.replace("{{data-block}}", dataBlock.toString());

    return template;
  }
}
