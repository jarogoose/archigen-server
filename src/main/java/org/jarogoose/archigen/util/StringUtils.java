package org.jarogoose.archigen.util;

public class StringUtils {

  public static String[] splitByUpperCase(String str) {
    return str.split("(?=\\p{Upper})");
  }

}
