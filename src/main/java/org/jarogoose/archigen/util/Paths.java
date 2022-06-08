package org.jarogoose.archigen.util;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.capitalize;

import lombok.AllArgsConstructor;
import org.jarogoose.archigen.Application;

@AllArgsConstructor
public enum Paths {
  CONTROLLER_PATH("control"),
  API_PATH("api"),
  REQUEST_PATH("domain/model/request"),
  RESPONSE_PATH("domain/model/response"),
  DTO_PATH("domain/model/dto"),
  DTO_MAPPER_PATH("domain/mapper"),
  STORAGE_PATH("storage"),
  EXCEPTION_PATH("domain/exception");

  private final String value;

  public String get(String root, String feature, String postfix) {
    String dir = Application.PROPERTIES.get("project.dir");
    String home = Application.PROPERTIES.get("user.home");
    String artefact = Application.PROPERTIES.get("project.artefact").replace(".", "/");
    String project = Application.PROPERTIES.get("project.name");
    return format("%s/%s/src/main/java/%s/%s/feature/%s/%s/%s%s.java",
        home, dir, artefact, project, root, this.value, capitalize(feature), postfix);
  }

  @Override
  public String toString() {
    return this.value;
  }

}
