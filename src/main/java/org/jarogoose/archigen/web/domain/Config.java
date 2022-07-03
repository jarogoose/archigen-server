package org.jarogoose.archigen.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Config(
    @JsonProperty("id") String id,
    @JsonProperty("artefact") String artefact,
    @JsonProperty("project") String project,
    @JsonProperty("baseDir") String baseDir
) {

}
