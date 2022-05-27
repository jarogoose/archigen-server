package org.jarogoose.archigen.service;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import org.jarogoose.archigen.util.Packages;

public class ImportContainerSingleton {

  private static final StringBuilder controllerImports = new StringBuilder();

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

  public static ImportContainerSingleton instance() {
    return new ImportContainerSingleton();
  }
}
