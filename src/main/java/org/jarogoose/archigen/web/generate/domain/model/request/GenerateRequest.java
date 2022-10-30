package org.jarogoose.archigen.web.generate.domain.model.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
public record GenerateRequest(
        @JsonProperty("projectName") String projectName,
        @JsonProperty("feature") String feature,
        @JsonProperty("package") String root,
        @JsonProperty("restApi") String restApi,
        @JsonProperty("readWrite") String readWrite,
        @JsonProperty("data") List<String> data) {
}
