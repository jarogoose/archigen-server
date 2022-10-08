package org.jarogoose.archigen.core;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.template.api.FacadeActionTemplate;
import org.jarogoose.archigen.core.template.api.FacadeSummaryTemplate;
import org.jarogoose.archigen.core.template.api.ServiceReadTemplate;
import org.jarogoose.archigen.core.template.api.ServiceWriteTemplate;
import org.jarogoose.archigen.core.template.control.ControllerActionTemplate;
import org.jarogoose.archigen.core.template.control.ControllerSummaryTemplate;
import org.jarogoose.archigen.core.template.domain.DtoMapperTemplate;
import org.jarogoose.archigen.core.template.domain.ExceptionTemplate;
import org.jarogoose.archigen.core.template.domain.ModelTemplate;
import org.jarogoose.archigen.core.template.domain.RequestTemplate;
import org.jarogoose.archigen.core.template.domain.ResponseTemplate;
import org.jarogoose.archigen.core.template.storage.EntityMapperTemplate;
import org.jarogoose.archigen.core.template.storage.EntityTemplate;
import org.jarogoose.archigen.core.template.storage.LoaderTemplate;
import org.jarogoose.archigen.core.template.storage.StorageTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.springframework.stereotype.Component;

@Component
public class ArchigenGenerator {

  public List<ArcTemplate> prepare(Config config, Domain domain) {
    List<ArcTemplate> templates = new ArrayList<>();

    // add general domain templates
    templates.add(new ModelTemplate(config, domain));
    templates.add(new DtoMapperTemplate(config, domain));
    templates.add(new ExceptionTemplate(config, domain));

    // add storage templates
    templates.add(new EntityTemplate(config, domain));
    templates.add(new EntityMapperTemplate(config, domain));
    templates.add(new LoaderTemplate(config, domain));
    templates.add(new StorageTemplate(config, domain));

    // add read templates
    templates.add(new ResponseTemplate(config, domain));
    templates.add(new ControllerSummaryTemplate(config, domain));
    templates.add(new FacadeSummaryTemplate(config, domain));
    templates.add(new ServiceReadTemplate(config, domain));

    // add write templates
    templates.add(new RequestTemplate(config, domain));
    templates.add(new ControllerActionTemplate(config, domain));
    templates.add(new FacadeActionTemplate(config, domain));
    templates.add(new ServiceWriteTemplate(config, domain));

    return templates;
  }

  public void generate(List<ArcTemplate> templates) throws IOException {
    for (ArcTemplate template : templates) {
      final String content = template.content();
      final File file = template.file();
      Files.createParentDirs(file);
      Files.asCharSink(file, StandardCharsets.UTF_8).write(content);
    }
  }

  public List<String> preview(List<ArcTemplate> templates) {
    List<String> contents = new ArrayList<>();

    for (ArcTemplate template : templates) {
      contents.add(template.content());
    }

    return contents;
  }
}
