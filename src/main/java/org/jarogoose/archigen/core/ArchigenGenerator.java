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
    final Config config,
    final Domain domain,
    final TemplateFilter filter
  ) {
    final List<ArcTemplate> templates = new ArrayList<>();

    for (final TemplateKey key : filter.templateKeys()) {
      templates.add(ArcTemplate.of(key, config, domain));
    }

    return List.copyOf(templates);
  }

  public void generate(final List<ArcTemplate> templates) throws IOException {
    for (final ArcTemplate template : templates) {
      final String content = template.content();
      final File file = template.file();
      Files.createParentDirs(file);
      Files.asCharSink(file, StandardCharsets.UTF_8).write(content);
    }
  }

  public List<String> preview(final List<ArcTemplate> templates) {
    final List<String> contents = new ArrayList<>();

    for (final ArcTemplate template : templates) {
      contents.add(template.content());
    }

    return contents;
  }
}
