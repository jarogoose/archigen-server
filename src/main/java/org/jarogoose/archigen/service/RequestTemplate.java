package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.REQUEST_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.DATA;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
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
    template = template.replace(PACKAGE.toString(), packageName);

    // feature name
    String featureName = format("%s", capitalize(request.control()));
    template = template.replace(FEATURE.toString(), featureName);

    // data block
    StringBuilder dataBlock = new StringBuilder();
    for (int i = 0; i < request.data().size(); i++) {
      dataBlock.append(format("  private String %s;",
          request.data().get(i)));
      if (i != request.data().size() - 1) {
        dataBlock.append(System.lineSeparator());
      }
    }
    template = template.replace(DATA.toString(), dataBlock.toString());

    return template;
  }

}
