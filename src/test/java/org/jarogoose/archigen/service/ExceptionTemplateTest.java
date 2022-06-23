package org.jarogoose.archigen.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Exception template generation")
class ExceptionTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.domain.exception;
      
      public class DailyMetabolicActivityNotFoundException extends RuntimeException {
      
        public DailyMetabolicActivityNotFoundException(String errorMessage) {
          super(errorMessage);
        }
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Exception class generated and formatted as expected")
  public void generateExceptionClass() {
    ExceptionTemplate template = new ExceptionTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}