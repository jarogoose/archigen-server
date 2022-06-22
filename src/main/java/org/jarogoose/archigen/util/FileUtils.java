package org.jarogoose.archigen.util;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileUtils {

  public static String readFile(String path, Charset encoding) {
    try {
      return Files.asCharSource(new File(path), encoding).read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
