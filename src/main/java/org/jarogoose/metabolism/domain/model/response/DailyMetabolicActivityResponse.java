package org.jarogoose.metabolism.domain.model.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DailyMetabolicActivityResponse {
  private String id;
  private String userId;
  private String date;
  private String fat;
  private String carb;
  private String protein;
  private String fiber;
  private String water;
  private String sleep;
}
