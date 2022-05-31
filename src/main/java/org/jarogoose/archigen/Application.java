package org.jarogoose.archigen;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.util.FileUtils;

public class Application {

  public static final Map<String, String> PROPERTIES = new HashMap<>();

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);

    System.out.println("[INFO] Arhigen started");

    try (InputStream input = Application.class.getClassLoader()
        .getResourceAsStream("application.properties")) {

      if (input == null) {
        System.out.println("[ERROR] Properties file was not found");
        return;
      }

      Properties prop = new Properties();
      prop.load(input);

      for (final String name : prop.stringPropertyNames()) {
        PROPERTIES.put(name, prop.getProperty(name));
      }
      System.out.println("[INFO] Application properties initialized");

    } catch (IOException ex) {
      ex.printStackTrace();
    }

    while (true) {
      String[] commands = scanner.nextLine().split(" ");

      if (commands[0].equalsIgnoreCase("exit")) {
        System.exit(0);
      }

      if (commands[0].equalsIgnoreCase("domain")) {
        Domain domain = FileUtils.readDomainObject(commands[1]);

        if (Objects.isNull(domain)) {
          continue;
        }

        Thread task = new Thread(new GeneratorTask(domain));
        task.start();
      }
    }
  }
}
