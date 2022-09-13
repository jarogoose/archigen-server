package org.jarogoose.archigen;

import static org.jarogoose.archigen.util.Paths.API_PATH;
import static org.jarogoose.archigen.util.Paths.CONTROLLER_PATH;
import static org.jarogoose.archigen.util.Paths.DTO_MAPPER_PATH;
import static org.jarogoose.archigen.util.Paths.DTO_PATH;
import static org.jarogoose.archigen.util.Paths.EXCEPTION_PATH;
import static org.jarogoose.archigen.util.Paths.REQUEST_PATH;
import static org.jarogoose.archigen.util.Paths.RESPONSE_PATH;
import static org.jarogoose.archigen.util.Paths.STORAGE_PATH;

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
import org.jarogoose.archigen.web.domain.Config;
import org.springframework.stereotype.Component;

@Component
public class ArchigenGenerator {

  public void generateAll(Config config, Domain domain) throws IOException {
    createEntityFile(config, domain);
    createModelFile(config, domain);
    createRequestFile(config, domain);
    createResponseFile(config, domain);
    createDtoMapperFile(config, domain);
    createNotFoundExceptionFile(config, domain);
    createControllerFile(config, domain);
    createFacadeFile(config, domain);
    createServiceFile(config, domain);
    createLoaderFile(config, domain);
    createStorageFile(config, domain);
    createEntityMapperFile(config, domain);
  }

  public void createEntityFile(Config config, Domain domain) throws IOException {
    EntityTemplate entityTemplate = new EntityTemplate();
    String entityContent = entityTemplate.createTemplate(domain);
    File entityFile = new File(STORAGE_PATH.get(config, domain.root(), domain.feature(), "Entity"));
    Files.createParentDirs(entityFile);
    Files.asCharSink(entityFile, StandardCharsets.UTF_8).write(entityContent);
  }

  public void createModelFile(Config config, Domain domain) throws IOException {
    ModelTemplate modelTemplate = new ModelTemplate();
    String modelContent = modelTemplate.createTemplate(domain);
    File modelFile = new File(DTO_PATH.get(config, domain.root(), domain.feature(), ""));
    Files.createParentDirs(modelFile);
    Files.asCharSink(modelFile, StandardCharsets.UTF_8).write(modelContent);
  }

  public void createRequestFile(Config config, Domain domain) throws IOException {
    RequestTemplate requestTemplate = new RequestTemplate();
    for (Request request : domain.requests()) {
      String requestContent = requestTemplate.createTemplate(domain, request);
      File requestFile = new File(REQUEST_PATH.get(config, domain.root(), request.control(), "Request"));
      Files.createParentDirs(requestFile);
      Files.asCharSink(requestFile, StandardCharsets.UTF_8).write(requestContent);
    }
  }

  public void createResponseFile(Config config, Domain domain) throws IOException {
    ResponseTemplate responseTemplate = new ResponseTemplate();
    String responseContent = responseTemplate.createTemplate(domain);
    File responseFile = new File(RESPONSE_PATH.get(config, domain.root(), domain.feature(), "Response"));
    Files.createParentDirs(responseFile);
    Files.asCharSink(responseFile, StandardCharsets.UTF_8).write(responseContent);
  }

  public void createDtoMapperFile(Config config, Domain domain) throws IOException {
    DtoMapperTemplate dtoMapperTemplate = new DtoMapperTemplate();
    String dtoMapperContent = dtoMapperTemplate.createTemplate(domain);
    File dtoMapperFile = new File(DTO_MAPPER_PATH.get(config, domain.root(), domain.feature(), "Mapper"));
    Files.createParentDirs(dtoMapperFile);
    Files.asCharSink(dtoMapperFile, StandardCharsets.UTF_8).write(dtoMapperContent);
  }

  public void createNotFoundExceptionFile(Config config, Domain domain) throws IOException {
    ExceptionTemplate exceptionTemplate = new ExceptionTemplate();
    String exceptionContent = exceptionTemplate.createTemplate(domain);
    File exceptionFile = new File(
        EXCEPTION_PATH.get(config, domain.root(), domain.feature(), "NotFoundException"));
    Files.createParentDirs(exceptionFile);
    Files.asCharSink(exceptionFile, StandardCharsets.UTF_8).write(exceptionContent);
  }

  public void createControllerFile(Config config, Domain domain) throws IOException {
    ControllerTemplate controllerTemplate = new ControllerTemplate();
    String controllerContent = controllerTemplate.createTemplate(domain);
    File controllerFile = new File(
        CONTROLLER_PATH.get(config, domain.root(), domain.feature(), "Controller"));
    Files.createParentDirs(controllerFile);
    Files.asCharSink(controllerFile, StandardCharsets.UTF_8).write(controllerContent);
  }

  public void createFacadeFile(Config config, Domain domain) throws IOException {
    FacadeTemplate facadeTemplate = new FacadeTemplate();
    String facadeContent = facadeTemplate.createTemplate(domain);
    File facadeFile = new File(API_PATH.get(config, domain.root(), domain.feature(), "Facade"));
    Files.createParentDirs(facadeFile);
    Files.asCharSink(facadeFile, StandardCharsets.UTF_8).write(facadeContent);
  }

  public void createServiceFile(Config config, Domain domain) throws IOException {
    ServiceTemplate serviceTemplate = new ServiceTemplate();
    String serviceContent = serviceTemplate.createTemplate(domain);
    File serviceFile = new File(API_PATH.get(config, domain.root(), domain.feature(), "Service"));
    Files.createParentDirs(serviceFile);
    Files.asCharSink(serviceFile, StandardCharsets.UTF_8).write(serviceContent);
  }

  public void createLoaderFile(Config config, Domain domain) throws IOException {
    LoaderTemplate loaderTemplate = new LoaderTemplate();
    String loaderContent = loaderTemplate.createTemplate(domain);
    File loaderFile = new File(STORAGE_PATH.get(config, domain.root(), domain.feature(), "Loader"));
    Files.createParentDirs(loaderFile);
    Files.asCharSink(loaderFile, StandardCharsets.UTF_8).write(loaderContent);
  }

  public void createStorageFile(Config config, Domain domain) throws IOException {
    StorageTemplate storageTemplate = new StorageTemplate();
    String storageContent = storageTemplate.createTemplate(domain);
    File storageFile = new File(STORAGE_PATH.get(config, domain.root(), domain.feature(), "Storage"));
    Files.createParentDirs(storageFile);
    Files.asCharSink(storageFile, StandardCharsets.UTF_8).write(storageContent);
  }

  public void createEntityMapperFile(Config config, Domain domain) throws IOException {
    EntityMapperTemplate entityMapperTemplate = new EntityMapperTemplate();
    String entityMapperContent = entityMapperTemplate.createTemplate(domain);
    File entityMapperFile = new File(
        STORAGE_PATH.get(config, domain.root(), domain.feature(), "EntityMapper"));
    Files.createParentDirs(entityMapperFile);
    Files.asCharSink(entityMapperFile, StandardCharsets.UTF_8).write(entityMapperContent);
  }
}
