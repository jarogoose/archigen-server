package org.jarogoose.archigen.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Api {
  @JsonProperty("requests") private List<Request> requests;
}
