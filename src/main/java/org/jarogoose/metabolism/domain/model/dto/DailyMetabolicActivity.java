package org.jarogoose.metabolism.domain.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class DailyMetabolicActivity {
  private final String id;
  private final String userId;
  private final String date;
  private final String fat;
  private final String carb;
  private final String protein;
  private final String fiber;
  private final String water;
  private final String sleep;
}
