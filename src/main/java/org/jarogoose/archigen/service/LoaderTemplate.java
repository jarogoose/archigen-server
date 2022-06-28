package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Commons.formatDtoImport;
import static org.jarogoose.archigen.util.Commons.formatDtoToEntityStaticImport;
import static org.jarogoose.archigen.util.Commons.formatEntityToDtoStaticImport;
import static org.jarogoose.archigen.util.Commons.formatNotFoundExceptionImport;
import static org.jarogoose.archigen.util.ImportContainerSingleton.imports;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
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
import org.jarogoose.archigen.util.ReturnType;
import org.springframework.http.HttpMethod;

public class LoaderTemplate {

  public static final String TEMPLATE = """
      package {{package}};
            
      import java.util.List;
      import lombok.extern.slf4j.Slf4j;
      {{imports}}
      import org.springframework.stereotype.Repository;
            
      @Slf4j
      @Repository
      public class {{feature-name}}Loader {
            
      {{dependency-block}}
      {{api-block}}
      }
      """;

  public static final String DEPENDENCY_BLOCK_TEMPLATE = """
        private final {{feature-name}}Storage storage;
              
        public {{feature-name}}Loader({{feature-name}}Storage storage) {
          this.storage = storage;
        }
      """;

  public static final String API_READ_BLOCK_TEMPLATE = """
        public {{feature-name}} {{query-name}} {
          return storage.{{storage-query-name}}
              .map({{feature-name}}EntityMapper::toDto)
              .orElseThrow(() -> new {{feature-name}}NotFoundException(
                  "[{{root-name}}] {{domain-text}} was not found"));
        }
      """;

  public static final String API_READ_ALL_BLOCK_TEMPLATE = """
        public List<{{feature-name}}> {{query-name}} {
          return toDto(storage.{{storage-query-name}});
        }
      """;

  public static final String API_WRITE_BLOCK_TEMPLATE = """
        public void {{query-name}} {
          storage.{{storage-query-name}};
        }
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // dto import
    imports().addLoaderImports(formatDtoImport(domain));

    // entity  mapper static import
    imports().addLoaderImports(formatEntityToDtoStaticImport(domain));
    imports().addLoaderImports(formatDtoToEntityStaticImport(domain));

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(DEPENDENCY.toString(), createDependencyBlock(domain));
    template = template.replace(API.toString(), createApiBlock(domain));
    template = template.replace(IMPORTS.toString(), imports().getLoaderImports());

    return template;
  }

  public String createDependencyBlock(Domain domain) {
    String dependencyBlock = DEPENDENCY_BLOCK_TEMPLATE;

    dependencyBlock = dependencyBlock.replace(FEATURE.toString(), capitalize(domain.feature()));

    return dependencyBlock;
  }

  public CharSequence createApiBlock(Domain domain) {
    StringBuilder content = new StringBuilder();

    for (Request request : domain.requests()) {
      if (request.returnType().equalsIgnoreCase(ReturnType.OBJECT.name())) {
        formatReadLoaderApi(domain, request, content);
      } else if (request.returnType().equalsIgnoreCase(ReturnType.COLLECTION.name())) {
        formatReadAllLoaderApi(domain, request, content);
      } else {
        formatWriteLoaderApi(domain, request, content);
      }
    }
    content.deleteCharAt(content.length() - 1);

    return content.toString();
  }

  private void formatReadLoaderApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_READ_BLOCK_TEMPLATE;

    // exception import
    imports().addLoaderImports(formatNotFoundExceptionImport(domain));

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

    // query api name
    String queryApiName = "";
    if (request.customQuery()) {
      queryApiName = constructCustomLoaderQuery(request);
    } else {
      queryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{query-name}}", queryApiName);

    // storage query api name
    String storageQueryApiName = "";
    if (request.customQuery()) {
      storageQueryApiName = constructCustomStorageQuery(request);
    } else {
      storageQueryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{storage-query-name}}", storageQueryApiName);

    // root name
    apiBlock = apiBlock.replace("{{root-name}}", domain.root().toUpperCase());

    // domain text
    apiBlock = apiBlock.replace("{{domain-text}}", formatDomainText(domain.feature()));

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatReadAllLoaderApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_READ_ALL_BLOCK_TEMPLATE;

    // exception import
    imports().addLoaderImports(formatNotFoundExceptionImport(domain));

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), featureName);

    // query api name
    String queryApiName = "";
    if (request.customQuery()) {
      queryApiName = constructCustomLoaderQuery(request);
    } else {
      queryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{query-name}}", queryApiName);

    // storage query api name
    String storageQueryApiName = "";
    if (request.customQuery()) {
      storageQueryApiName = constructCustomStorageQuery(request);
    } else {
      storageQueryApiName = format("%s(dto)", request.query());
    }
    apiBlock = apiBlock.replace("{{storage-query-name}}", storageQueryApiName);

    // root name
    apiBlock = apiBlock.replace("{{root-name}}", domain.root().toUpperCase());

    // domain text
    apiBlock = apiBlock.replace(FEATURE.toString(), formatDomainText(domain.feature()));

    content.append(apiBlock).append(System.lineSeparator());
  }

  private void formatWriteLoaderApi(Domain domain, Request request, StringBuilder content) {
    String apiBlock = API_WRITE_BLOCK_TEMPLATE;

    // feature class
    String featureClass = format("%s", capitalize(domain.feature()));
    apiBlock = apiBlock.replace(FEATURE.toString(), featureClass);

    if (request.customQuery()) {
      apiBlock = apiBlock.replace("{{query-name}}", constructCustomLoaderQuery(request));
    } else {
      // query api name
      String queryApi = format("%s(%s dto)", request.query(), featureClass);
      apiBlock = apiBlock.replace("{{query-name}}", queryApi);
    }

    // storage query api name
    String storageQueryApiName = "";
    if (request.customQuery()) {
      storageQueryApiName = constructCustomStorageQuery(request);
    } else {
      if (request.httpMethod().equalsIgnoreCase(HttpMethod.PUT.name())) {
        storageQueryApiName = "save(toEntity(dto))";
      } else {
        storageQueryApiName = request.query() + "(toEntity(dto))";
      }
    }
    apiBlock = apiBlock.replace("{{storage-query-name}}", storageQueryApiName);

    content.append(apiBlock).append(System.lineSeparator());
  }

  private String formatDomainText(String feature) {
    String[] words = splitByUpperCase(feature);
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(word.toLowerCase()).append(" ");
    }
    sb.deleteCharAt(sb.length() - 1);
    return sb.toString();
  }

  private String constructCustomLoaderQuery(Request request) {
    // build custom query for example getByUserName(String id)
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
        sb.append("String ").append(word.toLowerCase());
        isFirstWord = false;
        continue;
      }

      if (isPropertyFound) {
        sb.append(capitalize(word));
      }
    }

    sb.append(")");
    return sb.toString();
  }

  private String constructCustomStorageQuery(Request request) {
    // build custom query for example getByUserName(String id)
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
        sb.append(word.toLowerCase());
        isFirstWord = false;
        continue;
      }

      if (isPropertyFound) {
        sb.append(capitalize(word));
      }
    }

    sb.append(")");
    return sb.toString();
  }

}
