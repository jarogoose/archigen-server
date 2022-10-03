package org.jarogoose.archigen.core.template;

import static org.springframework.util.StringUtils.capitalize;
import java.io.File;
import java.util.List;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.util.Paths;
import org.jarogoose.archigen.web.domain.Config;

public class DtoMapperTemplate implements ArcTemplate {

  private static final String TEMPLATE = """
    package {{project-path}}.feature.{{root-name}}.domain.mapper;

    import {{project-path}}.feature.{{root-name}}.domain.model.dto.{{feature-name}};
    import {{project-path}}.feature.{{root-name}}.domain.model.request.Add{{feature-name}}Request;
    import {{project-path}}.feature.{{root-name}}.domain.model.response.ShowAll{{feature-name}}Response;
    import java.util.List;
    import lombok.experimental.UtilityClass;
    
    @UtilityClass
    public class {{feature-name}}Mapper {
    
      static {{feature-name}} toDto(Add{{feature-name}}Request request) {
        return {{feature-name}}.builder()
    {{dto-data}}
            .build();
      }
    
      static ShowAll{{feature-name}}Response toResponse(List<{{feature-name}}> dtos) {
        return ShowAll{{feature-name}}Response.builder()
            .{{feature-name-lowercase}}(dtos)
            .build();
      }
    }
  """;

private final Config config;
private final Domain domain;

public DtoMapperTemplate(Config config, Domain domain) {
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
    final String featureNameLowercase = domain.feature();

    String template = TEMPLATE;

    template = template.replace("{{project-path}}", projectPath);
    template = template.replace("{{root-name}}", domain.root());
    template = template.replace("{{feature-name}}", featureName);
    template =
      template.replace("{{feature-name-lowercase}}", featureNameLowercase);
    template =
      template.replace("{{dto-data}}", formatEntityData(domain.data()));

    return template;
  }

  @Override
  public File file() {
    return new File(Paths.DTO_MAPPER_PATH
      .get(config, domain.root(), domain.feature(), "Mapper"));
  }

  private String formatEntityData(List<String> data) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("        .")
        .append(field)
        .append("(request.")
        .append(field)
        .append("())")
        .append(System.lineSeparator());
    }
    return sb.toString();
  }
}
