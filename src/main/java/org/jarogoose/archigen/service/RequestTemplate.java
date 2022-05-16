package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Packages.REQUEST_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class RequestTemplate {

  public String createTemplate(Domain domain, Request request) {
    String filePath = "src/main/resources/template/domain/request-pojo.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // package
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), REQUEST_PACKAGE);
    template = template.replace("{{package}}", packageName);

    // class name
    String className = format("%s", capitalize(request.name()));
    template = template.replace("{{class-name}}", className);

    // data block
    StringBuilder dataBlock = new StringBuilder();
    for (int i = 0; i < request.data().size(); i++) {
      dataBlock.append(format("  private String %s;",
          request.data().get(i)));
      if (i != request.data().size() - 1) {
        dataBlock.append(System.lineSeparator());
      }
    }
    template = template.replace("{{data-block}}", dataBlock.toString());

    return template;
  }

}
