package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Packages.EXCEPTION_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.domain.Domain;

public class ExceptionTemplate {

  private static final String TEMPLATE = """
      package {{package}};
            
      public class {{feature-name}}NotFoundException extends RuntimeException {
      
        public {{feature-name}}NotFoundException(String errorMessage) {
          super(errorMessage);
        }
      
      }
            
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // package
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), EXCEPTION_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);

    return template;
  }
}
