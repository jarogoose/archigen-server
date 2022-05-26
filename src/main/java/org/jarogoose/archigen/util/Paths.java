package org.jarogoose.archigen.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Paths {
  ROOT_PROJECT_PATH("src/main/java/org/jarogoose"),
  CONTROLLER_PATH("control"),
  API_PATH("api"),
  REQUEST_PATH("domain/model/request"),
  RESPONSE_PATH("domain/model/response"),
  DTO_PATH("domain/model/dto"),
  STORAGE_PATH("storage"),
  EXCEPTION_PATH("domain/exception");

  private final String value;

  @Override
  public String toString() {
    return this.value;
  }

}
