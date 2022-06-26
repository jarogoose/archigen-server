package org.jarogoose.archigen.service;

import org.jarogoose.archigen.service.gwt.Given;
import org.jarogoose.archigen.service.gwt.Then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Model template generation")
class ModelTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.domain.model.dto;
      
      import lombok.Builder;
      
      @Builder
      public record DailyMetabolicActivity(
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
  @DisplayName("[FEATURE] Model class generated and formatted as expected")
  public void generateModelClass() {
    ModelTemplate template = new ModelTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
