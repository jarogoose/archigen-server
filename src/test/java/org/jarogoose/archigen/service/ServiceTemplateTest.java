package org.jarogoose.archigen.service;

import org.jarogoose.archigen.service.gwt.Given;
import org.jarogoose.archigen.service.gwt.Then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Service template generation")
class ServiceTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.api;
      
      import java.util.List;
      import lombok.extern.slf4j.Slf4j;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.dto.DailyMetabolicActivity;
      import com.jarogoose.enenbi.feature.metabolism.storage.DailyMetabolicActivityLoader;
      import org.springframework.stereotype.Service;
      
      @Slf4j
      @Service
      class DailyMetabolicActivityService {
      
        private final DailyMetabolicActivityLoader loader;
      
        DailyMetabolicActivityService(DailyMetabolicActivityLoader loader) {
          this.loader = loader;
        }
      
        public void add(DailyMetabolicActivity dto) {
          loader.save(dto);
        }
      
        public void modify(DailyMetabolicActivity dto) {
          loader.update(dto);
        }
      
        public DailyMetabolicActivity search(DailyMetabolicActivity dto) {
          return loader.findById(dto.id());
        }
      
        public List<DailyMetabolicActivity> showAll(DailyMetabolicActivity dto) {
          return loader.findAllByUserId(dto.userId());
        }
      
        public void remove(DailyMetabolicActivity dto) {
          loader.deleteById(dto.id());
        }
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Service class generated and formatted as expected")
  public void generateServiceClass() {
    ServiceTemplate template = new ServiceTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }

}
