package org.jarogoose.metabolism.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AddDailyMetabolicActivityRequest {
  private String userId;
  private String date;
  private String fat;
  private String carb;
  private String protein;
  private String fiber;
  private String water;
  private String sleep;
}
