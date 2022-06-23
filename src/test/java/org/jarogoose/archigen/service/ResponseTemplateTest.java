package org.jarogoose.archigen.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Response template generation")
class ResponseTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.domain.model.response;
      
      import lombok.Builder;
      
      @Builder
      public record DailyMetabolicActivityResponse(
          String id,
          String userId,
          String date,
          String fat,
          String carb,
          String protein,
          String fiber,
          String water,
          String sleep
      ) {
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Response class generated and formatted as expected")
  public void generateResponseClass() {
    ResponseTemplate template = new ResponseTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
