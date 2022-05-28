package org.jarogoose.archigen.util;

import static org.jarogoose.archigen.util.Packages.API_PACKAGE;
import static org.jarogoose.archigen.util.Packages.DTO_MAPPER_PACKAGE;
import static org.jarogoose.archigen.util.Packages.DTO_PACKAGE;
import static org.jarogoose.archigen.util.Packages.EXCEPTION_PACKAGE;
import static org.jarogoose.archigen.util.Packages.REQUEST_PACKAGE;
import static org.jarogoose.archigen.util.Packages.RESPONSE_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.jarogoose.archigen.util.Packages.STORAGE_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class Commons {

  public static String formatResponseImport(Domain domain) {
    return String.format("import %s.%s.%s.%sResponse;",
        ROOT_PACKAGE, domain.root(), RESPONSE_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatRequestImport(Domain domain, Request request) {
    return String.format("import %s.%s.%s.%sRequest;",
        ROOT_PACKAGE, domain.root(), REQUEST_PACKAGE, capitalize(request.control()));
  }

  public static String formatFacadeImport(Domain domain) {
    return String.format("import %s.%s.%s.%sFacade;",
        ROOT_PACKAGE, domain.root(), API_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatLoaderImport(Domain domain) {
    return String.format("import %s.%s.%s.%sLoader;",
        ROOT_PACKAGE, domain.root(), STORAGE_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatNotFoundExceptionImport(Domain domain) {
    return String.format("import %s.%s.%s.%sNotFoundException;",
        ROOT_PACKAGE, domain.root(), EXCEPTION_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatDtoImport(Domain domain) {
    return String.format("import %s.%s.%s.%s;",
        ROOT_PACKAGE, domain.root(), DTO_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatToDtoStaticImport(Domain domain) {
    return String.format("import static %s.%s.%s.%sMapper.toDto;",
        ROOT_PACKAGE, domain.root(), DTO_MAPPER_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatToResponseStaticImport(Domain domain) {
    return String.format("import static %s.%s.%s.%sMapper.toResponse;",
        ROOT_PACKAGE, domain.root(), DTO_MAPPER_PACKAGE, capitalize(domain.feature()));
  }
}
