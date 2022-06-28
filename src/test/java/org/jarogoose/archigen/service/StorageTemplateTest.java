package org.jarogoose.archigen.service;

import org.jarogoose.archigen.service.gwt.Given;
import org.jarogoose.archigen.service.gwt.Then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Storage template generation")
class StorageTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.storage;
      
      import java.util.List;
      import org.springframework.data.mongodb.repository.MongoRepository;
      import org.springframework.stereotype.Repository;
      
      @Repository
      interface DailyMetabolicActivityStorage extends MongoRepository<DailyMetabolicActivityEntity, String> {
      
        List<DailyMetabolicActivityEntity> findAllByUserId(String userId);
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Storage class generated and formatted as expected")
  public void generateStorageClass() {
    StorageTemplate template = new StorageTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
