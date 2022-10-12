package org.jarogoose.archigen.core.template;

import static org.jarogoose.archigen.core.Util.splitByUpperCase;
import static org.springframework.util.StringUtils.capitalize;

import java.io.File;
import java.util.List;
import org.jarogoose.archigen.core.TemplateKey;
import org.jarogoose.archigen.core.template.api.FacadeActionTemplate;
import org.jarogoose.archigen.core.template.api.FacadeSummaryTemplate;
import org.jarogoose.archigen.core.template.api.ServiceReadTemplate;
import org.jarogoose.archigen.core.template.api.ServiceWriteTemplate;
import org.jarogoose.archigen.core.template.control.ControllerActionTemplate;
import org.jarogoose.archigen.core.template.control.ControllerSummaryTemplate;
import org.jarogoose.archigen.core.template.domain.DtoMapperTemplate;
import org.jarogoose.archigen.core.template.domain.DtoTemplate;
import org.jarogoose.archigen.core.template.domain.ExceptionTemplate;
import org.jarogoose.archigen.core.template.domain.RequestTemplate;
import org.jarogoose.archigen.core.template.domain.ResponseTemplate;
import org.jarogoose.archigen.core.template.storage.EntityMapperTemplate;
import org.jarogoose.archigen.core.template.storage.EntityTemplate;
import org.jarogoose.archigen.core.template.storage.LoaderTemplate;
import org.jarogoose.archigen.core.template.storage.StorageTemplate;
import org.jarogoose.archigen.core.template.testing.BehavioralTestTemplate;
import org.jarogoose.archigen.core.template.testing.GivenTemplate;
import org.jarogoose.archigen.core.template.testing.StorageWrapperTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

public interface ArcTemplate {
  String content();
  File file();

  static ArcTemplate of(
    final TemplateKey key,
    final Config config,
    final Domain domain
  ) {
    return switch (key) {
      case CONTROLLER_ACTION -> new ControllerActionTemplate(config, domain);
      case CONTROLLER_SUMMARY -> new ControllerSummaryTemplate(config, domain);
      case FACADE_ACTION -> new FacadeActionTemplate(config, domain);
      case FACADE_SUMMARY -> new FacadeSummaryTemplate(config, domain);
      case SERVICE_READ -> new ServiceReadTemplate(config, domain);
      case SERVICE_WRITE -> new ServiceWriteTemplate(config, domain);
      case LOADER -> new LoaderTemplate(config, domain);
      case STORAGE -> new StorageTemplate(config, domain);
      case DTO -> new DtoTemplate(config, domain);
      case DTO_MAPPER -> new DtoMapperTemplate(config, domain);
      case ENTITY -> new EntityTemplate(config, domain);
      case ENTITY_MAPPER -> new EntityMapperTemplate(config, domain);
      case EXCEPTION -> new ExceptionTemplate(config, domain);
      case REQUEST -> new RequestTemplate(config, domain);
      case RESPONSE -> new ResponseTemplate(config, domain);
      case BEHAVIORAL_TEST -> new BehavioralTestTemplate(config, domain);
      case STORAGE_WRAPPER -> new StorageWrapperTemplate(config, domain);
      case GIVEN -> new GivenTemplate(config, domain);
      default -> throw new IllegalArgumentException("Unexpected archigen template key - : " + key);
    };
  }

  default String replaceAuthorName(final String template, final String author) {
    return template.replace("{{author-name}}", author);
  }

  default String replaceProjectPath(
    final String template,
    final String artefact,
    final String project
  ) {
    final String projectPath = String.format("%s.%s", artefact, project);
    return template.replace("{{project-path}}", projectPath);
  }

  default String replaceRootName(final String template, final String root) {
    return template.replace("{{root-name}}", root);
  }

  default String replaceRestApi(final String template, final String restApi) {
    return template.replace("{{rest-api}}", restApi);
  }

  default String replaceDomainUri(final String template, final String root) {
    return template.replace("{{domain-uri}}", root);
  }

  default String replaceUri(final String template, final String feature) {
    final String uriName = String
      .join("-", splitByUpperCase(feature))
      .toLowerCase();
    return template.replace("{{uri}}", uriName);
  }

  default String replaceFeatureName(
    final String template,
    final String feature
  ) {
    return template.replace("{{feature-name}}", capitalize(feature));
  }

  default String replaceFeatureNameLowered(
    final String template,
    final String feature
  ) {
    return template.replace("{{feature-name-lowercase}}", feature);
  }

  default String replaceFeatureNameUppercase(
    final String template,
    final String feature
  ) {
    final String value = String
      .join(" ", splitByUpperCase(feature))
      .toUpperCase();
    return template.replace("{{feature-name-uppercase}}", value);
  }

  default String replaceDocumentName(
    final String template,
    final String feature
  ) {
    StringBuilder sb = new StringBuilder();
    for (String word : splitByUpperCase(feature)) {
      sb.append(word.toLowerCase()).append("_");
    }
    sb.deleteCharAt(sb.length() - 1);
    return template.replace("{{document-name}}", sb.toString());
  }

  default String replaceFeatureAsText(
    final String template,
    final String feature
  ) {
    StringBuilder sb = new StringBuilder();
    for (String word : splitByUpperCase(feature)) {
      sb.append(word.toLowerCase()).append(" ");
    }
    sb.deleteCharAt(sb.length() - 1);
    return template.replace("{{feature-as-text}}", sb.toString());
  }

  default String replaceFeatureConstant(
    final String template,
    final String feature
  ) {
    StringBuilder sb = new StringBuilder();
    for (String word : splitByUpperCase(feature)) {
      sb.append(word.toLowerCase()).append("_");
    }
    sb.deleteCharAt(sb.length() - 1);
    return template.replace("{{feature-constant}}", sb.toString().toUpperCase());
  }

  default String replaceDtoData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    String ")
        .append(field)
        .append(",")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 2);

    return template.replace("{{data}}", sb.toString());
  }

  default String replaceRequestData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    @JsonProperty(\"")
        .append(field)
        .append("\") String ")
        .append(field)
        .append(",")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 2);

    return template.replace("{{data}}", sb.toString());
  }

  default String replaceToDtoData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("        .")
        .append(field)
        .append("(request.")
        .append(field)
        .append("())")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{data}}", sb.toString());
  }

  default String replaceEntityData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("private String ")
        .append(field.toLowerCase())
        .append(";")
        .append(System.lineSeparator());
    }

    return template.replace("{{data}}", sb.toString());
  }

  default String replaceEntityToDtoData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    .")
        .append(field)
        .append("(entity.get")
        .append(capitalize(field))
        .append("())")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{entity-to-dto-data}}", sb.toString());
  }

  default String replaceDtoToEntityData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("    .")
        .append(field)
        .append("(dto.")
        .append(field)
        .append("())")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{dto-to-entity-data}}", sb.toString());
  }

  default String replaceTestEntityData(
    final String template,
    final List<String> data
  ) {
    StringBuilder sb = new StringBuilder();
    for (String field : data) {
      sb
        .append("      .")
        .append(field)
        .append("(\"")
        .append(field)
        .append("\")")
        .append(System.lineSeparator());
    }
    sb.setLength(sb.length() - 1);

    return template.replace("{{test-entity-data}}", sb.toString());
  }
}
