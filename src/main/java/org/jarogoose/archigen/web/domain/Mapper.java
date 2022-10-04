package org.jarogoose.archigen.web.domain;

import org.jarogoose.archigen.web.storage.ConfigEntity;

public class Mapper {

  public static Config toDto(ConfigEntity entity) {
    return Config
      .builder()
      .id(entity.getId())
      .projectName(entity.getProjectName())
      .artefact(entity.getArtefact())
      .project(entity.getProject())
      .baseDir(entity.getBaseDir())
      .author(entity.getAuthor())
      .build();
  }

  public static ConfigEntity toEntity(Config dto) {
    return ConfigEntity
      .builder()
      .id(dto.id())
      .projectName(dto.projectName())
      .artefact(dto.artefact())
      .project(dto.project())
      .baseDir(dto.baseDir())
      .author(dto.author())
      .build();
  }
}
