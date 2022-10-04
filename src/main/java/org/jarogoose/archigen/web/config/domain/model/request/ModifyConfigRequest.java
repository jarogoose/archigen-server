package org.jarogoose.archigen.web.config.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModifyConfigRequest(
    @JsonProperty("id") String id,
    @JsonProperty("projectName") String projectName,
    @JsonProperty("artefact") String artefact,
    @JsonProperty("project") String project,
    @JsonProperty("baseDir") String baseDir,
    @JsonProperty("author") String author
) {}
