package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.jarogoose.archigen.util.Replacer.API;
import static org.jarogoose.archigen.util.Replacer.FEATURE;
import static org.jarogoose.archigen.util.Replacer.PACKAGE;
import static org.jarogoose.archigen.util.StringUtils.splitByUpperCase;
import static org.springframework.util.StringUtils.capitalize;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.util.ReturnType;

public class StorageTemplate {

  public static final String TEMPLATE = """
      package {{package}};
            
      import java.util.List;
      import org.springframework.data.mongodb.repository.MongoRepository;
      import org.springframework.stereotype.Repository;
            
      @Repository
      interface {{feature-name}}Storage extends MongoRepository<{{feature-name}}Entity, String> {
      
      {{api-block}}
      }
      """;

  public String createTemplate(Domain domain) {
    String template = TEMPLATE;

    // controller class
    String packageName = String.format("%s.%s.%s", ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE);
    template = template.replace(PACKAGE.toString(), packageName);
    template = template.replace(FEATURE.toString(), capitalize(domain.feature()));
    template = template.replace(API.toString(), createApiBlock(domain));

    return template;
  }

  public CharSequence createApiBlock(Domain domain) {
    StringBuilder content = new StringBuilder();

    for (Request request : domain.requests()) {
      if (request.customQuery()
          && request.returnType().equalsIgnoreCase(ReturnType.COLLECTION.name())) {
        content.append(format("  List<%sEntity> %s;",
            capitalize(domain.feature()),
            constructCustomLoaderCall(request)))
            .append(System.lineSeparator());
      }
    }

    return content.toString();
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
}
