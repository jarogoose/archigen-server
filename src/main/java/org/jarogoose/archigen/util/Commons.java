package org.jarogoose.archigen.util;

import static org.jarogoose.archigen.util.Packages.REQUEST_PACKAGE;
import static org.jarogoose.archigen.util.Packages.RESPONSE_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class Commons {

  public static String formatResponseImport(Domain domain) {
    return String.format("%s.%s.%s.%sResponse;",
        ROOT_PACKAGE, domain.root(), RESPONSE_PACKAGE, capitalize(domain.feature()));
  }

  public static String formatRequestImport(Domain domain, Request request) {
    return String.format("%s.%s.%s.%sRequest;",
        ROOT_PACKAGE, domain.root(), REQUEST_PACKAGE, capitalize(request.control()));
  }

}
