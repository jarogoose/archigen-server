package org.jarogoose.archigen.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.jarogoose.archigen.Application;
import org.jarogoose.archigen.domain.Domain;

public class FileUtils {

  public static String readFile(String path, Charset encoding) {
    try {
      return Files.asCharSource(new File(path), encoding).read();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Domain readDomainObject(String fileName) {
    try {
      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

      String dir = Application.PROPERTIES.get("domain.object.dir");
      String home = Application.PROPERTIES.get("user.home");
      String path = String.format("%s/%s%s.yaml", home, dir, fileName);

      System.out.println("[INFO] Domain object path " + path);

      return mapper.readValue(new File(path), Domain.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
