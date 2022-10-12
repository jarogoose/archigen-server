package org.jarogoose.archigen.core;

import java.util.List;

public enum TemplateKey {
  CONTROLLER_ACTION,
  CONTROLLER_SUMMARY,
  FACADE_ACTION,
  FACADE_SUMMARY,
  SERVICE_READ,
  SERVICE_WRITE,
  LOADER,
  STORAGE,
  ENTITY,
  ENTITY_MAPPER,
  EXCEPTION,
  DTO,
  DTO_MAPPER,
  REQUEST,
  RESPONSE,
  STORAGE_WRAPPER,
  BEHAVIORAL_TEST,
  GIVEN;

  public static List<TemplateKey> all() {
    return List.of(values());
  }
}
