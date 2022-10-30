package org.jarogoose.archigen.web.config.domain.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ModifyConfigRequest(
        @JsonProperty(value = "id", required = true) String id,
        @JsonProperty(value = "projectName", required = true) String projectName,
        @JsonProperty(value = "artefact", required = true) String artefact,
        @JsonProperty(value = "project", required = true) String project,
        @JsonProperty(value = "baseDir", required = true) String baseDir,
        @JsonProperty(value = "author", required = true) String author) {
}
