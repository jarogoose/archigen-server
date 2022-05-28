package org.jarogoose.archigen.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Replacer {
  PACKAGE("{{package}}"),
  FEATURE("{{feature-name}}"),
  DEPENDENCY("{{dependency-block}}"),
  API("{{api-block}}"),
  IMPORTS("{{imports}}"),
  REQUEST("{{request-name}}"),
  DATA("{{data-block}}");

  private final String value;

  @Override
  public String toString() {
    return this.value;
  }
}
