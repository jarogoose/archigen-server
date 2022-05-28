package org.jarogoose.archigen.service;

import static java.lang.String.format;

public class ImportContainerSingleton {

  private static final StringBuilder controllerImports = new StringBuilder();
  private static final StringBuilder facadeImports = new StringBuilder();
  private static final StringBuilder dtoMapperImports = new StringBuilder();


  private ImportContainerSingleton() {
  }

  public void addControllerImport(String line) {
    String formattedImport = format("import %s", line);
    if (!controllerImports.toString().contains(formattedImport)) {
      controllerImports.append(formattedImport).append(System.lineSeparator());
    }
  }

  public String getControllerImports() {
    controllerImports.setLength(controllerImports.length() - 1);
    return controllerImports.toString();
  }

  public void addFacadeImport(String line) {
    String formattedImport = format("import %s", line);
    if (!facadeImports.toString().contains(formattedImport)) {
      facadeImports.append(formattedImport).append(System.lineSeparator());
    }
  }

  public String getFacadeImports() {
    facadeImports.setLength(facadeImports.length() - 1);
    return facadeImports.toString();
  }

  public void addDtoMapperImportsImport(String line) {
    String formattedImport = format("import %s", line);
    if (!dtoMapperImports.toString().contains(formattedImport)) {
      dtoMapperImports.append(formattedImport).append(System.lineSeparator());
    }
  }

  public String getDtoMapperImportsFacadeImports() {
    dtoMapperImports.setLength(dtoMapperImports.length() - 1);
    return dtoMapperImports.toString();
  }

  public static ImportContainerSingleton imports() {
    return new ImportContainerSingleton();
  }
}
