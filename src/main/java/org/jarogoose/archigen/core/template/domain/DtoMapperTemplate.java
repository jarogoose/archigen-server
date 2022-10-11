package org.jarogoose.archigen.core.template.domain;

import java.io.File;
import org.jarogoose.archigen.core.Paths;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

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
  {{data}}
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
    String template = TEMPLATE;

    template = replaceAuthorName(template, config.author());
    template =
      replaceProjectPath(template, config.artefact(), config.project());
    template = replaceRootName(template, domain.root());
    template = replaceFeatureName(template, domain.feature());
    template = replaceFeatureNameLowered(template, domain.feature());
    template = replaceToDtoData(template, domain.data());

    return template;
  }

  @Override
  public File file() {
    return new File(
      Paths.DTO_MAPPER_PATH.get(
        config,
        domain.root(),
        domain.feature(),
        "Mapper",
        false
      )
    );
  }
}
