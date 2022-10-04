package org.jarogoose.archigen.web.config.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record SaveConfigRequest(
    @JsonProperty("projectName") String projectName,
    @JsonProperty("artefact") String artefact,
    @JsonProperty("project") String project,
    @JsonProperty("baseDir") String baseDir,
    @JsonProperty("author") String author
) {}
