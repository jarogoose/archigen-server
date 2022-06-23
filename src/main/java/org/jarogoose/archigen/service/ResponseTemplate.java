package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Packages.RESPONSE_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.DATA;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.domain.Domain;

public class ResponseTemplate {

  private static final String TEMPLATE = """
      package {{package}};
            
      import lombok.Builder;
            
      @Builder
      public record {{feature-name}}Response(
      {{data-block}}
      ) {
            
      }
            
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // package
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), RESPONSE_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);

    // data block
    StringBuilder dataBlock = new StringBuilder();
    for (int i = 0; i < domain.data().size(); i++) {
      dataBlock.append(format("    String %s", domain.data().get(i)));
      if (i != domain.data().size() - 1) {
        dataBlock.append(",").append(System.lineSeparator());
      }
    }
    template = template.replace(DATA.toString(), dataBlock.toString());

    return template;
  }
}
