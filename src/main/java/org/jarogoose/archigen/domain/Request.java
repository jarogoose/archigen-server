package org.jarogoose.archigen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Request {
  @JsonProperty("name") private String name;
  @JsonProperty("type") private String type;
  @JsonProperty("data") private List<String> data;
}
