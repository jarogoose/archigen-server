package org.jarogoose.archigen.core;

import static java.lang.String.format;
import static org.springframework.util.StringUtils.capitalize;

import lombok.AllArgsConstructor;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;

@AllArgsConstructor
public enum Paths {
  CONTROLLER_PATH("control"),
  API_PATH("api"),
  REQUEST_PATH("domain/model/request"),
  RESPONSE_PATH("domain/model/response"),
  DTO_PATH("domain/model/dto"),
  DTO_MAPPER_PATH("domain/mapper"),
  STORAGE_PATH("storage"),
  EXCEPTION_PATH("domain/exception"),
  STORAGE_WRAPPER_PATH("domain/exception");

  private final String value;

  public String get(
    Config config,
    String root,
    String feature,
    String postfix,
    boolean isTest
  ) {
    String dir = config.baseDir();
    String artefact = config.artefact().replace(".", "/");
    String project = config.project();
    return format(
      "%s/src/%s/java/%s/%s/feature/%s/%s/%s%s.java",
      dir,
      isTest ? "test" : "main",
      artefact,
      project,
      root,
      this.value,
      capitalize(feature),
      postfix
    );
  }

  @Override
  public String toString() {
    return this.value;
  }
}
