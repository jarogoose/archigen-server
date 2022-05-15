package org.jarogoose.archigen.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Paths {
  ROOT_PROJECT_PATH("src/main/java/org/jarogoose"),
  CONTROLLER_PATH("control"),
  REQUEST_PATH("domain/model/request"),
  DTO_PATH("domain/model/dto"),
  STORAGE_PATH("storage"),
  EXCEPTION_PATH("domain/exception");

  private final String value;

  @Override
  public String toString() {
    return this.value;
  }

}
