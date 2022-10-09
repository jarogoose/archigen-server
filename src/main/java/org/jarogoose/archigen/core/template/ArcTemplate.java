package org.jarogoose.archigen.core.template;

import static org.jarogoose.archigen.core.Util.splitByUpperCase;
import java.io.File;
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
      default -> throw new IllegalArgumentException("Unexpected archigen template key - : " + key);
    };
  }

  default String replaceFeatureNameUppercase(final String template, final String feature) {
    final String value = String.join(" ", splitByUpperCase(feature)).toUpperCase();
    return template.replace("{{feature-name-uppercase}}", value);
  }
}
