package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.DEPENDENCY;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.IMPORTS;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.jarogoose.archigen.util.StringUtils.splitByUpperCase;
import static org.springframework.util.StringUtils.capitalize;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.Commons;
import org.jarogoose.archigen.util.ReturnType;
import org.jarogoose.archigen.util.StringUtils;

public class ServiceTemplate {

  public static final String TEMPLATE = """
      package {{package}};
            
      import java.util.List;
      import lombok.extern.slf4j.Slf4j;
      {{imports}}
      import org.springframework.stereotype.Service;
            
      @Slf4j
      @Service
      class {{feature-name}}Service {
            
      {{dependency-block}}
      {{api-block}}
      }
            
      """;

  public static final String DEPENDENCY_BLOCK_TEMPLATE = """
        private final {{feature-name}}Loader loader;
              
        {{feature-name}}Service({{feature-name}}Loader loader) {
          this.loader = loader;
        }
      """;

  public static final String API_READ_BLOCK_TEMPLATE = """
        public {{feature-name}} {{service-api-name}}({{feature-name}} dto) {
          return loader.{{storage-query-api-name}};
        }
      """;

  public static final String API_READ_ALL_BLOCK_TEMPLATE = """
        public List<{{feature-name}}> {{service-api-name}}({{feature-name}} dto) {
          return loader.{{storage-query-api-name}};
        }
      """;

  public static final String API_WRITE_BLOCK_TEMPLATE = """
        public void {{service-api-name}}({{feature-name}} dto) {
          loader.{{storage-query-api-name}};
        }
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // dto import
    imports().addServiceImports(Commons.formatDtoImport(domain));

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), API_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getServiceImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlock = DEPENDENCY_BLOCK_TEMPLATE;

    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

    // loader import
    imports().addServiceImports(Commons.formatLoaderImport(domain));

    return dependencyBlock;
  }

  public CharSequence createApiBlock(Domain domain) {
    StringBuilder content = new StringBuilder();
    for (Request request : domain.requests()) {
      if (request.returnType().equalsIgnoreCase(ReturnType.OBJECT.name())) {
        formatReadServiceApi(domain, request, content);
      } else if (request.returnType().equalsIgnoreCase(ReturnType.COLLECTION.name())) {
        formatReadAllServiceApi(domain, request, content);
      } else {
        formatWritServiceApi(domain, request, content);
      }
    }
    content.deleteCharAt(content.length() - 1);
    return content.toString();
  }

  private void formatReadServiceApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_READ_BLOCK_TEMPLATE;

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    // storage query api name
    String storageQueryApiName = "";
    if (request.customQuery()) {
      storageQueryApiName = constructCustomLoaderCall(request);
    } else {
      storageQueryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatReadAllServiceApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_READ_ALL_BLOCK_TEMPLATE;

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    // storage query api name
    String storageQueryApiName = "";
    if (request.customQuery()) {
      storageQueryApiName = constructCustomLoaderCall(request);
    } else {
      storageQueryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatWritServiceApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_WRITE_BLOCK_TEMPLATE;

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

    // service api name
    String serviceApiName = format("%s", request.execute());
    apiBlock = apiBlock.replace("{{service-api-name}}", serviceApiName);

    // storage query api name
    String storageQueryApiName = "";
    if (request.customQuery()) {
      storageQueryApiName = constructCustomLoaderCall(request);
    } else {
      storageQueryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{storage-query-api-name}}", storageQueryApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private String constructCustomLoaderCall(Request request) {
    // build custom query for example getByUserName(dto.userName())
    StringBuilder sb = new StringBuilder();
    sb.append(request.query()).append("(");
    String[] words = splitByUpperCase(request.query());

    Queue<String> stack = new LinkedList<>(Arrays.asList(words));

    boolean isPropertyFound = false;
    boolean isFirstWord = true;

    while (!stack.isEmpty()) {
      String word = stack.poll();

      if (word.equalsIgnoreCase("by")) {
        isPropertyFound = true;
        isFirstWord = true;
        continue;
      }

      if (isPropertyFound && isFirstWord) {
        sb.append("dto.").append(word.toLowerCase());
        isFirstWord = false;
        continue;
      }

      if (isPropertyFound) {
        sb.append(capitalize(word));
      }
    }

    sb.append("())");
    return sb.toString();
  }

}
