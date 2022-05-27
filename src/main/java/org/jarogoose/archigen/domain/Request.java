package org.jarogoose.archigen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Request {
  @JsonProperty("control") private String control;
  @JsonProperty("execute") private String execute;
  @JsonProperty("query") private String query;
  @JsonProperty(value = "custom-query",defaultValue = "false") private boolean customQuery;
  @JsonProperty("type") private String type;
  @JsonProperty("data") private List<String> data;
}
