package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatRequestImport;
import static org.jarogoose.archigen.util.Commons.formatResponseImport;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.DEPENDENCY;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.jarogoose.archigen.util.Replacer.REQUEST;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.Commons;
import org.jarogoose.archigen.util.ReturnType;

public class FacadeTemplate {

  private static final String TEMPLATE = """
      package {{package}};
      
      {{imports}}
      import java.util.List;
      import lombok.extern.slf4j.Slf4j;
      import org.springframework.stereotype.Service;
            
      @Slf4j
      @Service
      public class {{feature-name}}Facade {
            
      {{dependency-block}}
      {{api-block}}
      }
      """;

  private static final String DEPENDENCY_BLOCK_TEMPLATE = """
        private final {{feature-name}}Service service;
              
        public {{feature-name}}Facade({{feature-name}}Service service) {
          this.service = service;
        }
      """;

  private static final String API_READ_BLOCK_TEMPLATE = """
        public {{feature-name}}Response {{facade-api-name}}({{request-name}}Request request) {
          return toResponse(service.{{service-api-name}}(toDto(request)));
        }
      """;

  private static final String API_READ_ALL_BLOCK_TEMPLATE = """
        public List<{{feature-name}}Response> {{facade-api-name}}({{request-name}}Request request) {
          return toResponse(service.{{service-api-name}}(toDto(request)));
        }
      """;

  private static final String API_WRITE_BLOCK_TEMPLATE = """
        public void {{facade-api-name}}({{request-name}}Request request) {
          service.{{service-api-name}}(toDto(request));
        }
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // mapper static import
    imports().addFacadeImport(Commons.formatRequestToDtoStaticImport(domain));
    imports().addFacadeImport(Commons.formatDtoToResponseStaticImport(domain));

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), API_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getFacadeImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlock = DEPENDENCY_BLOCK_TEMPLATE;

    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

    return dependencyBlock;
  }

  public String createApiBlock(Domain domain) {
    // response import
    imports().addFacadeImport(formatResponseImport(domain));

    StringBuilder content = new StringBuilder();
    for (Request request : domain.requests()) {
      if (request.returnType().equalsIgnoreCase(ReturnType.OBJECT.name())) {
        formatReadFacadeApi(domain, request, content);
      } else if (request.returnType().equalsIgnoreCase(ReturnType.COLLECTION.name())) {
        formatReadAllFacadeApi(domain, request, content);
      } else {
        formatWriteFacadeApi(domain, request, content);
      }
    }
    content.deleteCharAt(content.length() - 1);

    return content.toString();
  }

  private void formatReadFacadeApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_READ_BLOCK_TEMPLATE;

    // domain name
    String domainName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), domainName);

    // facade api name
    String facadeApiName = format("%s", request.control());
    apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

    // request name
    String requestName = format("%s", capitalize(request.control()));
    apiBlock = apiBlock.replace(REQUEST.toString(), requestName);

    // request import
    imports().addFacadeImport(formatRequestImport(domain, request));

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatReadAllFacadeApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_READ_ALL_BLOCK_TEMPLATE;

    // domain name
    String domainName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), domainName);

    // facade api name
    String facadeApiName = format("%s", request.control());
    apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

    // request name
    String requestName = format("%s", capitalize(request.control()));
    apiBlock = apiBlock.replace(REQUEST.toString(), requestName);

    // request import
    imports().addFacadeImport(formatRequestImport(domain, request));

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatWriteFacadeApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_WRITE_BLOCK_TEMPLATE;

    // facade api name
    String facadeApiName = format("%s", request.control());
    apiBlock = apiBlock.replace("{{facade-api-name}}", facadeApiName);

    // request name
    String requestName = format("%s", capitalize(request.control()));
    apiBlock = apiBlock.replace(REQUEST.toString(), requestName);

    // request import
    imports().addFacadeImport(formatRequestImport(domain, request));

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }
}
