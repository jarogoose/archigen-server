package org.jarogoose.archigen.core.util;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.capitalize;

import org.jarogoose.archigen.web.config.domain.model.dto.Config;

import lombok.AllArgsConstructor;

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

  public String get(Config config, String root, String feature, String postfix) {
    String dir = config.baseDir();
    String home = "/home/jarogoose";
    String artefact = config.artefact().replace(".", "/");
    String project = config.project();
    return format("%s/%s/src/main/java/%s/%s/feature/%s/%s/%s%s.java",
        home, dir, artefact, project, root, this.value, capitalize(feature), postfix);
  }

  @Override
  public String toString() {
    return this.value;
  }

}
