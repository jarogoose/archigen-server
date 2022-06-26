package org.jarogoose.archigen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public record Request(
    @JsonProperty("control") String control,
    @JsonProperty("execute") String execute,
    @JsonProperty("query") String query,
    @JsonProperty(value = "custom-query", defaultValue = "false") boolean customQuery,
    @JsonProperty("http-method") String httpMethod,
    @JsonProperty("return-type") String returnType, // VOID, OBJECT, COLLECTION
    @JsonProperty("data") List<String> data
) {

}
