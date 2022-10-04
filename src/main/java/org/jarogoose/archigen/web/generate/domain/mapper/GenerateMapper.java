package org.jarogoose.archigen.web.generate.domain.mapper;

import lombok.experimental.UtilityClass;

import org.jarogoose.archigen.web.generate.domain.model.dto.Domain;
import org.jarogoose.archigen.web.generate.domain.model.request.GenerateRequest;

@UtilityClass
public class GenerateMapper {

  public static Domain toDomainDto(GenerateRequest request) {
    return Domain
      .builder()
      .feature(request.feature())
      .root(request.root())
      .restApi(request.restApi())
      .readWrite(request.readWrite())
      .data(request.data())
      .build();
  }
}
