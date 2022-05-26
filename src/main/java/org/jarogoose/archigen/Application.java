package org.jarogoose.archigen;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Paths.CONTROLLER_PATH;
import static org.jarogoose.archigen.util.Paths.DTO_PATH;
import static org.jarogoose.archigen.util.Paths.API_PATH;
import static org.jarogoose.archigen.util.Paths.STORAGE_PATH;
import static org.jarogoose.archigen.util.Paths.EXCEPTION_PATH;
import static org.jarogoose.archigen.util.Paths.REQUEST_PATH;
import static org.jarogoose.archigen.util.Paths.ROOT_PROJECT_PATH;
import static org.springframework.util.StringUtils.capitalize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.google.common.io.Files;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.service.ControllerTemplate;
import org.jarogoose.archigen.service.EntityTemplate;
import org.jarogoose.archigen.service.ExceptionTemplate;
import org.jarogoose.archigen.service.FacadeTemplate;
import org.jarogoose.archigen.service.ModelTemplate;
import org.jarogoose.archigen.service.RequestTemplate;
import org.jarogoose.archigen.service.ServiceTemplate;

public class Application {

  public static void main(String[] args) throws IOException {
    // Read domain object
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    Domain domain = mapper.readValue(
        new File("src/main/resources/domain/daily-metabolic-activity-domain.yaml"), Domain.class);

    // Create entity file
    EntityTemplate entityTemplate = new EntityTemplate();
    String entityContent = entityTemplate.createTemplate(domain);
    String entityClassName = capitalize(domain.feature());
    String entityFilePath = format("%s/%s/%s/%sEntity.java",
        ROOT_PROJECT_PATH, domain.root(), STORAGE_PATH, entityClassName);
    File entityFile = new File(entityFilePath);
    Files.createParentDirs(entityFile);
    Files.asCharSink(entityFile, StandardCharsets.UTF_8).write(entityContent);

    // Create model file
    ModelTemplate modelTemplate = new ModelTemplate();
    String modelContent = modelTemplate.createTemplate(domain);
    String modelClassName = capitalize(domain.feature());
    String modelFilePath = format("%s/%s/%s/%s.java",
        ROOT_PROJECT_PATH, domain.root(), DTO_PATH, modelClassName);
    File modelFile = new File(modelFilePath);
    Files.createParentDirs(modelFile);
    Files.asCharSink(modelFile, StandardCharsets.UTF_8).write(modelContent);

    // Create request file
    RequestTemplate requestTemplate = new RequestTemplate();
    for (Request request : domain.controller().requests()) {
      String requestContent = requestTemplate.createTemplate(domain, request);
      String requestClassName = capitalize(request.name());
      String requestFilePath = format("%s/%s/%s/%sRequest.java",
          ROOT_PROJECT_PATH, domain.root(), REQUEST_PATH, requestClassName);
      File requestFile = new File(requestFilePath);
      Files.createParentDirs(requestFile);
      Files.asCharSink(requestFile, StandardCharsets.UTF_8).write(requestContent);
    }

    // Create not found exception file
    ExceptionTemplate exceptionTemplate = new ExceptionTemplate();
    String exceptionContent = exceptionTemplate.createTemplate(domain);
    String exceptionClassName = capitalize(domain.feature());
    String exceptionFilePath = format("%s/%s/%s/%sNotFoundException.java",
        ROOT_PROJECT_PATH, domain.root(), EXCEPTION_PATH, exceptionClassName);
    File exceptionFile = new File(exceptionFilePath);
    Files.createParentDirs(exceptionFile);
    Files.asCharSink(exceptionFile, StandardCharsets.UTF_8).write(exceptionContent);

    // Create controller file
    ControllerTemplate controllerTemplate = new ControllerTemplate();
    String controllerContent = controllerTemplate.createTemplate(domain);
    String controllerClassName = capitalize(domain.feature());
    String controllerFilePath = format("%s/%s/%s/%sController.java",
        ROOT_PROJECT_PATH, domain.root(), CONTROLLER_PATH, controllerClassName);
    File controllerFile = new File(controllerFilePath);
    Files.createParentDirs(controllerFile);
    Files.asCharSink(controllerFile, StandardCharsets.UTF_8).write(controllerContent);

    // Create facade file
    FacadeTemplate facadeTemplate = new FacadeTemplate();
    String facadeContent = facadeTemplate.createTemplate(domain);
    String facadeClassName = capitalize(domain.feature());
    String facadeFilePath = format("%s/%s/%s/%sFacade.java",
        ROOT_PROJECT_PATH, domain.root(), API_PATH, facadeClassName);
    File facadeFile = new File(facadeFilePath);
    Files.createParentDirs(facadeFile);
    Files.asCharSink(facadeFile, StandardCharsets.UTF_8).write(facadeContent);

    // Create service file
    ServiceTemplate serviceTemplate = new ServiceTemplate();
    String serviceContent = serviceTemplate.createTemplate(domain);
    String serviceClassName = capitalize(domain.feature());
    String serviceFilePath = format("%s/%s/%s/%sService.java",
        ROOT_PROJECT_PATH, domain.root(), API_PATH, serviceClassName);
    File serviceFile = new File(serviceFilePath);
    Files.createParentDirs(serviceFile);
    Files.asCharSink(serviceFile, StandardCharsets.UTF_8).write(serviceContent);
  }
}
