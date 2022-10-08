package org.jarogoose.archigen.web.config.domain.model.response;

import java.util.List;

import org.jarogoose.archigen.web.config.domain.model.dto.Config;

import lombok.Builder;

@Builder
public record LoadAllConfigsResponse(
    List<Config> configs
) {}
