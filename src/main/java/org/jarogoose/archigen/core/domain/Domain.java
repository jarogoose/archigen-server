package org.jarogoose.archigen.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(fluent = true)
public record Domain(
    @JsonProperty("feature") String feature,
    @JsonProperty("package") String root,
    @JsonProperty("rest-api") String restApi,
    @JsonProperty("read-write") String readWrite,
    @JsonProperty("data") List<String> data
) {

}
