package org.jarogoose.archigen.service;

import org.jarogoose.archigen.service.gwt.Given;
import org.jarogoose.archigen.service.gwt.Then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Facade template generation")
class FacadeTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.api;
      
      import static com.jarogoose.enenbi.feature.metabolism.domain.mapper.DailyMetabolicActivityMapper.toDto;
      import static com.jarogoose.enenbi.feature.metabolism.domain.mapper.DailyMetabolicActivityMapper.toResponse;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.response.DailyMetabolicActivityResponse;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.AddDailyMetabolicActivityRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.ModifyDailyMetabolicActivityRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.SearchDailyMetabolicActivityRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.ShowAllDailyMetabolicActivitiesRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.RemoveDailyMetabolicActivityRequest;
      import java.util.List;
      import lombok.extern.slf4j.Slf4j;
      import org.springframework.stereotype.Service;
      
      @Slf4j
      @Service
      public class DailyMetabolicActivityFacade {
      
        private final DailyMetabolicActivityService service;
      
        public DailyMetabolicActivityFacade(DailyMetabolicActivityService service) {
          this.service = service;
        }
      
        public void addDailyMetabolicActivity(AddDailyMetabolicActivityRequest request) {
          service.add(toDto(request));
        }
      
        public void modifyDailyMetabolicActivity(ModifyDailyMetabolicActivityRequest request) {
          service.modify(toDto(request));
        }
      
        public DailyMetabolicActivityResponse searchDailyMetabolicActivity(SearchDailyMetabolicActivityRequest request) {
          return toResponse(service.search(toDto(request)));
        }
      
        public List<DailyMetabolicActivityResponse> showAllDailyMetabolicActivities(ShowAllDailyMetabolicActivitiesRequest request) {
          return toResponse(service.showAll(toDto(request)));
        }
      
        public void removeDailyMetabolicActivity(RemoveDailyMetabolicActivityRequest request) {
          service.remove(toDto(request));
        }
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Model class generated and formatted as expected")
  public void generateModelClass() {
    FacadeTemplate template = new FacadeTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
