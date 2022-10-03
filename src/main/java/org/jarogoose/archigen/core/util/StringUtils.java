package org.jarogoose.archigen.core.util;

public class StringUtils {

  public static String[] splitByUpperCase(String str) {
    return str.split("(?=\\p{Upper})");
  }

}
