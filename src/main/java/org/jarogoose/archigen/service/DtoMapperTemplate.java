package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatDtoImport;
import static org.jarogoose.archigen.util.Commons.formatRequestImport;
import static org.jarogoose.archigen.util.Commons.formatResponseImport;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.DTO_MAPPER_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.jarogoose.archigen.util.Replacer.REQUEST;
import static org.springframework.util.StringUtils.capitalize;

import java.util.List;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class DtoMapperTemplate {

  private static final String TEMPLATE = """
      package {{package}};

      {{imports}}
      import java.util.ArrayList;
      import java.util.List;

      public class {{feature-name}}Mapper {

      {{request-to-dto-block}}
      {{dto-to-response-block}}
      {{dtos-to-requests-block}}
      }

      """;

  private static final String REQUEST_TO_DTO_BLOCK_TEMPLATE = """
        public static {{feature-name}} toDto({{request-name}}Request request) {
          return {{feature-name}}.builder()
      {{data-map-block}}
              .build();
        }
      """;

  private static final String DTO_TO_REQUEST_BLOCK_TEMPLATE = """
        public static {{feature-name}}Response toResponse({{feature-name}} dto) {
          return {{feature-name}}Response.builder()
      {{data-map-block}}
              .build();
        }
      """;

  public static final String DTOS_TO_REQUESTS_BLOCK_TEMPLATE = """
        public static List<{{feature-name}}Response> toResponse(List<{{feature-name}}> dtos) {
          List<{{feature-name}}Response> response = new ArrayList<>();
          for (var dto : dtos) {
            response.add(toResponse(dto));
          }
          return response;
        }
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // package
    String packageName = String.format("%s.%s.%s",
        ROOT_PACKAGE, domain.root(), DTO_MAPPER_PACKAGE);

    // dto import
    imports().addDtoMapperImports(formatDtoImport(domain));

    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{dto-to-response-block}}", createDtoToResponseBlock(domain));
    template = template.replace("{{request-to-dto-block}}", createRequestToDtoBlock(domain));
    template = template.replace("{{dtos-to-requests-block}}", createDtosToRequestsBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getDtoMapperImports());

    return template;
  }

  private CharSequence createDtosToRequestsBlock(Domain domain) {
    return DTOS_TO_REQUESTS_BLOCK_TEMPLATE.replace("{{feature-name}}", capitalize(domain.feature()));
  }

  private String createDtoToResponseBlock(Domain domain) {
    String template = DTO_TO_REQUEST_BLOCK_TEMPLATE;
    String mapPattern = ".%s(dto.%s())";

    // response import
    imports().addDtoMapperImports(formatResponseImport(domain));

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace(FEATURE.toString(), featureName);
    template = template.replace("{{data-map-block}}", iterateData(domain.data(), mapPattern));

    return template;
  }

  private String createRequestToDtoBlock(Domain domain) {
    String mapPattern = ".%s(request.%s())";
    StringBuilder requestToDtoBlock = new StringBuilder();

    for (Request request : domain.requests()) {
      String template = REQUEST_TO_DTO_BLOCK_TEMPLATE;

      // feature name
      String featureName = format("%s", capitalize(domain.feature()));
      template = template.replace(FEATURE.toString(), featureName);

      // request name
      String requestName = format("%s", capitalize(request.control()));
      template = template.replace(REQUEST.toString(), requestName);

      // request import
      imports().addDtoMapperImports(formatRequestImport(domain, request));

      template = template.replace("{{data-map-block}}", iterateData(request.payload(), mapPattern));

      requestToDtoBlock.append(template).append(System.lineSeparator());
    }

    return requestToDtoBlock.toString();
  }

  private String iterateData(List<String> data, String mapPattern) {
    // data block
    StringBuilder dataMapBlock = new StringBuilder();
    for (int i = 0; i < data.size(); i++) {
      dataMapBlock.append("        ").append(format(mapPattern, data.get(i), data.get(i)));
      if (i != data.size() - 1) {
        dataMapBlock.append(System.lineSeparator());
      }
    }
    return dataMapBlock.toString();
  }
}
