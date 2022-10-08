package org.jarogoose.archigen.core.template;

import lombok.experimental.UtilityClass;

import java.util.List;

import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;

@UtilityClass
public class Given {

  public Domain DOMAIN = Domain
    .builder()
    .feature("foodItem")
    .root("food")
    .restApi("user-ui")
    .readWrite("RW")
    .data(List.of("name", "category", "quantity"))
    .build();

  public Config CONFIG = Config
    .builder()
    .artefact("com.jarogoose")
    .project("enenbi")
    .baseDir("/home/user/path/to/project")
    .author("Yaroslav Kynyk")
    .build();
}
