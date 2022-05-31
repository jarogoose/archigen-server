package org.jarogoose.archigen;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.Paths.API_PATH;
import static org.jarogoose.archigen.util.Paths.CONTROLLER_PATH;
import static org.jarogoose.archigen.util.Paths.DTO_MAPPER_PATH;
import static org.jarogoose.archigen.util.Paths.DTO_PATH;
import static org.jarogoose.archigen.util.Paths.EXCEPTION_PATH;
import static org.jarogoose.archigen.util.Paths.REQUEST_PATH;
import static org.jarogoose.archigen.util.Paths.RESPONSE_PATH;
import static org.jarogoose.archigen.util.Paths.ROOT_PROJECT_PATH;
import static org.jarogoose.archigen.util.Paths.STORAGE_PATH;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;
import org.jarogoose.archigen.service.ControllerTemplate;
import org.jarogoose.archigen.service.DtoMapperTemplate;
import org.jarogoose.archigen.service.EntityMapperTemplate;
import org.jarogoose.archigen.service.EntityTemplate;
import org.jarogoose.archigen.service.ExceptionTemplate;
import org.jarogoose.archigen.service.FacadeTemplate;
import org.jarogoose.archigen.service.LoaderTemplate;
import org.jarogoose.archigen.service.ModelTemplate;
import org.jarogoose.archigen.service.RequestTemplate;
import org.jarogoose.archigen.service.ResponseTemplate;
import org.jarogoose.archigen.service.ServiceTemplate;
import org.jarogoose.archigen.service.StorageTemplate;

public class GeneratorTask implements Runnable {

  private final Domain domain;

  public GeneratorTask(Domain domain) {
    this.domain = domain;
  }

  @Override
  public void run() {
    try {
      generate(domain);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void generate(Domain domain) throws IOException {
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
    for (Request request : domain.api().requests()) {
      String requestContent = requestTemplate.createTemplate(domain, request);
      String requestClassName = capitalize(request.control());
      String requestFilePath = format("%s/%s/%s/%sRequest.java",
          ROOT_PROJECT_PATH, domain.root(), REQUEST_PATH, requestClassName);
      File requestFile = new File(requestFilePath);
      Files.createParentDirs(requestFile);
      Files.asCharSink(requestFile, StandardCharsets.UTF_8).write(requestContent);
    }

    // Create response file
    ResponseTemplate responseTemplate = new ResponseTemplate();
    String responseContent = responseTemplate.createTemplate(domain);
    String responseClassName = capitalize(domain.feature());
    String responseFilePath = format("%s/%s/%s/%sResponse.java",
        ROOT_PROJECT_PATH, domain.root(), RESPONSE_PATH, responseClassName);
    File responseFile = new File(responseFilePath);
    Files.createParentDirs(responseFile);
    Files.asCharSink(responseFile, StandardCharsets.UTF_8).write(responseContent);

    // Create dto mapper file
    DtoMapperTemplate dtoMapperTemplate = new DtoMapperTemplate();
    String dtoMapperContent = dtoMapperTemplate.createTemplate(domain);
    String dtoMapperClassName = capitalize(domain.feature());
    String dtoMapperFilePath = format("%s/%s/%s/%sMapper.java",
        ROOT_PROJECT_PATH, domain.root(), DTO_MAPPER_PATH, dtoMapperClassName);
    File dtoMapperFile = new File(dtoMapperFilePath);
    Files.createParentDirs(dtoMapperFile);
    Files.asCharSink(dtoMapperFile, StandardCharsets.UTF_8).write(dtoMapperContent);

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

    // Create loader file
    LoaderTemplate loaderTemplate = new LoaderTemplate();
    String loaderContent = loaderTemplate.createTemplate(domain);
    String loaderClassName = capitalize(domain.feature());
    String loaderFilePath = format("%s/%s/%s/%sLoader.java",
        ROOT_PROJECT_PATH, domain.root(), STORAGE_PATH, loaderClassName);
    File loaderFile = new File(loaderFilePath);
    Files.createParentDirs(loaderFile);
    Files.asCharSink(loaderFile, StandardCharsets.UTF_8).write(loaderContent);

    // Create storage file
    StorageTemplate storageTemplate = new StorageTemplate();
    String storageContent = storageTemplate.createTemplate(domain);
    String storageClassName = capitalize(domain.feature());
    String storageFilePath = format("%s/%s/%s/%sStorage.java",
        ROOT_PROJECT_PATH, domain.root(), STORAGE_PATH, storageClassName);
    File storageFile = new File(storageFilePath);
    Files.createParentDirs(storageFile);
    Files.asCharSink(storageFile, StandardCharsets.UTF_8).write(storageContent);

    // Create entity mapper file
    EntityMapperTemplate entityMapperTemplate = new EntityMapperTemplate();
    String entityMapperContent = entityMapperTemplate.createTemplate(domain);
    String entityMapperClassName = capitalize(domain.feature());
    String entityMapperFilePath = format("%s/%s/%s/%sEntityMapper.java",
        ROOT_PROJECT_PATH, domain.root(), STORAGE_PATH, entityMapperClassName);
    File entityMapperFile = new File(entityMapperFilePath);
    Files.createParentDirs(entityMapperFile);
    Files.asCharSink(entityMapperFile, StandardCharsets.UTF_8).write(entityMapperContent);
  }
}
