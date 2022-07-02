package org.jarogoose.archigen.web.domain;

import org.jarogoose.archigen.web.storage.ConfigEntity;

public class Mapper {

  public static Config toDto(ConfigEntity config) {
    return Config.builder()
        .id(config.getId())
        .artefact(config.getArtefact())
        .project(config.getProject())
        .baseDir(config.getBaseDir())
        .build();
  }

  public static ConfigEntity toEntity(Config config) {
    return ConfigEntity.builder()
        .id(config.id())
        .artefact(config.artefact())
        .project(config.project())
        .baseDir(config.baseDir())
        .build();
  }
}
