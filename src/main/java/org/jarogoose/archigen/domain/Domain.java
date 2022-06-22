package org.jarogoose.archigen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Domain {

  @JsonProperty("feature")
  private String feature;
  @JsonProperty("root")
  private String root;
  @JsonProperty("data")
  private List<String> data;
  @JsonProperty("requests")
  private List<Request> requests;
}
