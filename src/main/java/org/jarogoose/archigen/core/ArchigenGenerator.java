package org.jarogoose.archigen.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jarogoose.archigen.core.domain.Domain;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.core.template.ControllerActionTemplate;
import org.jarogoose.archigen.core.template.ControllerSummaryTemplate;
import org.jarogoose.archigen.core.template.DtoMapperTemplate;
import org.jarogoose.archigen.core.template.EntityMapperTemplate;
import org.jarogoose.archigen.core.template.EntityTemplate;
import org.jarogoose.archigen.core.template.ExceptionTemplate;
import org.jarogoose.archigen.core.template.FacadeActionTemplate;
import org.jarogoose.archigen.core.template.FacadeSummaryTemplate;
import org.jarogoose.archigen.core.template.LoaderTemplate;
import org.jarogoose.archigen.core.template.ModelTemplate;
import org.jarogoose.archigen.core.template.RequestTemplate;
import org.jarogoose.archigen.core.template.ResponseTemplate;
import org.jarogoose.archigen.core.template.ServiceReadTemplate;
import org.jarogoose.archigen.core.template.ServiceWriteTemplate;
import org.jarogoose.archigen.core.template.StorageTemplate;
import org.jarogoose.archigen.web.domain.Config;
import org.springframework.stereotype.Component;
import com.google.common.io.Files;

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
    for(ArcTemplate template : templates) {
      final String content = template.content();
      final File file = template.file();
      Files.createParentDirs(file);
      Files.asCharSink(file, StandardCharsets.UTF_8).write(content);
    }
  }
}
