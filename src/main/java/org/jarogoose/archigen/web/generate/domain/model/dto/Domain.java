package org.jarogoose.archigen.web.generate.domain.model.dto;

import java.util.List;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
public record Domain(
    String feature,
    String root,
    String restApi,
    String readWrite,
    List<String> data
) {}
