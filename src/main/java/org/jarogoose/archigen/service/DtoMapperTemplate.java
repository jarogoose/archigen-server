package org.jarogoose.archigen.service;

import static java.lang.String.format;
import static org.jarogoose.archigen.util.FileUtils.readFile;
import static org.jarogoose.archigen.util.Packages.DTO_MAPPER_PACKAGE;
import static org.jarogoose.archigen.util.Packages.ROOT_PACKAGE;
import static org.springframework.util.StringUtils.capitalize;

import com.google.common.base.Charsets;
import java.util.List;
import org.jarogoose.archigen.domain.Domain;
import org.jarogoose.archigen.domain.Request;

public class DtoMapperTemplate {

  public String createTemplate(Domain domain) {
    String filePath = "src/main/resources/template/domain/dto-mapper.template";
    String template = readFile(filePath, Charsets.UTF_8);

    // package
    String packageName = String.format("%s.%s.%s",
        ROOT_PACKAGE, domain.root(), DTO_MAPPER_PACKAGE);
    template = template.replace("{{package}}", packageName);

    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace("{{feature-name}}", featureName);
    template = template.replace("{{dto-to-response-block}}", createDtoToResponseBlock(domain));
    template = template.replace("{{request-to-dto-block}}", createRequestToDtoBlock(domain));

    return template;
  }

  private String createDtoToResponseBlock(Domain domain) {
    String filePath = "src/main/resources/template/domain/dto-to-response-block.template";
    String template = readFile(filePath, Charsets.UTF_8);
    String mapPattern = ".%s(dto.get%s())";

    // feature name
    String featureName = format("%s", capitalize(domain.feature()));
    template = template.replace("{{feature-name}}", featureName);
    template = template.replace("{{data-map-block}}", iterateData(domain.data(), mapPattern));

    return template;
  }

  private String createRequestToDtoBlock(Domain domain) {
    String mapPattern = ".%s(request.get%s())";
    StringBuilder requestToDtoBlock = new StringBuilder();

    for (Request request : domain.api().requests()) {
      String filePath = "src/main/resources/template/domain/request-to-dto-block.template";
      String template = readFile(filePath, Charsets.UTF_8);

      // feature name
      String featureName = format("%s", capitalize(domain.feature()));
      template = template.replace("{{feature-name}}", featureName);

      // request name
      String requestName = format("%s", capitalize(request.control()));
      template = template.replace("{{request-name}}", requestName);

      template = template.replace("{{data-map-block}}", iterateData(request.data(), mapPattern));

      requestToDtoBlock.append(template);
    }

    return requestToDtoBlock.toString();
  }

  private String iterateData(List<String> data, String mapPattern) {
    // data block
    StringBuilder dataMapBlock = new StringBuilder();
    for (int i = 0; i < data.size(); i++) {
      dataMapBlock.append(format(mapPattern, data.get(i), capitalize(data.get(i))));
      if (i != data.size() - 1) {
        dataMapBlock.append(System.lineSeparator());
      }
    }
    return dataMapBlock.toString();
  }
}
