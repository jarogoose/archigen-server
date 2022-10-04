package org.jarogoose.archigen.web.config.domain.mapper;

import java.util.List;
import org.jarogoose.archigen.web.config.domain.model.dto.Config;
import org.jarogoose.archigen.web.config.domain.model.request.ModifyConfigRequest;
import org.jarogoose.archigen.web.config.domain.model.request.SaveConfigRequest;
import org.jarogoose.archigen.web.config.storage.ConfigEntity;

public class ConfigMapper {

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

  public static List<Config> toDtos(List<ConfigEntity> entities) {
    List<Config> dtos = List.of();

    for (ConfigEntity entity : entities) {
      dtos.add(toDto(entity));
    }

    return dtos;
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

  public static Config toDto(SaveConfigRequest request) {
    return Config
      .builder()
      .projectName(request.projectName())
      .artefact(request.artefact())
      .project(request.project())
      .baseDir(request.baseDir())
      .author(request.author())
      .build();
  }

  public static Config toDto(ModifyConfigRequest request) {
    return Config
      .builder()
      .id(request.id())
      .projectName(request.projectName())
      .artefact(request.artefact())
      .project(request.project())
      .baseDir(request.baseDir())
      .author(request.author())
      .build();
  }
}
