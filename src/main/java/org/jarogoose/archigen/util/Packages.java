package org.jarogoose.archigen.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Packages {
  ROOT_PACKAGE("org.jarogoose.archigen"),
  EXCEPTION_PACKAGE("domain.exception"),
  REQUEST_PACKAGE("domain.model.request"),
  RESPONSE_PACKAGE("domain.model.response"),
  CONTROLLER_PACKAGE("control"),
  API_PACKAGE("api"),
  STORAGE_PACKAGE("storage"),
  DTO_PACKAGE("domain.model.dto");

  private final String value;

  @Override
  public String toString() {
    return this.value;
  }

}
