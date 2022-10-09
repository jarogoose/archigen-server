package org.jarogoose.archigen.core;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.jarogoose.archigen.core.template.ArcTemplate;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.jarogoose.archigen.web.generate.domain.model.dto.TemplateFilter;
import org.springframework.stereotype.Component;

@Component
public class ArchigenGenerator {

  public List<ArcTemplate> prepare(
    Config config,
    Domain domain,
    TemplateFilter filter
  ) {
    List<ArcTemplate> templates = new ArrayList<>();

    for (TemplateKey key : filter.templateKeys()) {
      templates.add(ArcTemplate.of(key, config, domain));
    }

    return List.copyOf(templates);
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
