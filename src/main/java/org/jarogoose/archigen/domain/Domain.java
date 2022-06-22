package org.jarogoose.archigen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public record Domain(
    @JsonProperty("feature") String feature,
    @JsonProperty("root") String root,
    @JsonProperty("data") List<String> data,
    @JsonProperty("requests") List<Request> requests
) {

}
