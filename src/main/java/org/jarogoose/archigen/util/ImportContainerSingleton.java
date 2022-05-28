package org.jarogoose.archigen.util;

import static java.lang.String.format;

public class ImportContainerSingleton {

  private static final StringBuilder controllerImports = new StringBuilder();
  private static final StringBuilder facadeImports = new StringBuilder();
  private static final StringBuilder serviceImports = new StringBuilder();
  private static final StringBuilder loaderImports = new StringBuilder();
  private static final StringBuilder dtoMapperImports = new StringBuilder();
  private static final StringBuilder entityMapperImports = new StringBuilder();


  private ImportContainerSingleton() {
  }

  public void addControllerImport(String line) {
    if (!controllerImports.toString().contains(line)) {
      controllerImports.append(line).append(System.lineSeparator());
    }
  }

  public String getControllerImports() {
    controllerImports.setLength(controllerImports.length() - 1);
    return controllerImports.toString();
  }

  public void addFacadeImport(String line) {
    if (!facadeImports.toString().contains(line)) {
      facadeImports.append(line).append(System.lineSeparator());
    }
  }

  public String getFacadeImports() {
    facadeImports.setLength(facadeImports.length() - 1);
    return facadeImports.toString();
  }

  public void addServiceImports(String line) {
    if (!serviceImports.toString().contains(line)) {
      serviceImports.append(line).append(System.lineSeparator());
    }
  }

  public String getServiceImports() {
    serviceImports.setLength(serviceImports.length() - 1);
    return serviceImports.toString();
  }

  public void addDtoMapperImports(String line) {
    if (!dtoMapperImports.toString().contains(line)) {
      dtoMapperImports.append(line).append(System.lineSeparator());
    }
  }

  public String getDtoMapperImportsFacadeImports() {
    dtoMapperImports.setLength(dtoMapperImports.length() - 1);
    return dtoMapperImports.toString();
  }

  public void addEntityMapperImports(String line) {
    if (!entityMapperImports.toString().contains(line)) {
      entityMapperImports.append(line).append(System.lineSeparator());
    }
  }

  public String getEntityMapperImportsFacadeImports() {
    entityMapperImports.setLength(entityMapperImports.length() - 1);
    return entityMapperImports.toString();
  }

  public void addLoaderImports(String line) {
    if (!loaderImports.toString().contains(line)) {
      loaderImports.append(line).append(System.lineSeparator());
    }
  }

  public String getLoaderImports() {
    loaderImports.setLength(loaderImports.length() - 1);
    return loaderImports.toString();
  }

  public static ImportContainerSingleton imports() {
    return new ImportContainerSingleton();
  }
}
