package org.jarogoose.archigen.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Dto mapper template generation")
class DtoMapperTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.domain.mapper;
            
      import com.jarogoose.enenbi.feature.metabolism.domain.model.dto.DailyMetabolicActivity;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.response.DailyMetabolicActivityResponse;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.AddDailyMetabolicActivityRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.ModifyDailyMetabolicActivityRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.SearchDailyMetabolicActivityRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.ShowAllDailyMetabolicActivitiesRequest;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.request.RemoveDailyMetabolicActivityRequest;
      import java.util.ArrayList;
      import java.util.List;
            
      public class DailyMetabolicActivityMapper {
            
        public static DailyMetabolicActivity toDto(AddDailyMetabolicActivityRequest request) {
          return DailyMetabolicActivity.builder()
              .userId(request.userId())
              .date(request.date())
              .fat(request.fat())
              .carb(request.carb())
              .protein(request.protein())
              .fiber(request.fiber())
              .water(request.water())
              .sleep(request.sleep())
              .build();
        }
            
        public static DailyMetabolicActivity toDto(ModifyDailyMetabolicActivityRequest request) {
          return DailyMetabolicActivity.builder()
              .id(request.id())
              .userId(request.userId())
              .date(request.date())
              .fat(request.fat())
              .carb(request.carb())
              .protein(request.protein())
              .fiber(request.fiber())
              .water(request.water())
              .sleep(request.sleep())
              .build();
        }
            
        public static DailyMetabolicActivity toDto(SearchDailyMetabolicActivityRequest request) {
          return DailyMetabolicActivity.builder()
              .id(request.id())
              .build();
        }
            
        public static DailyMetabolicActivity toDto(ShowAllDailyMetabolicActivitiesRequest request) {
          return DailyMetabolicActivity.builder()
              .userId(request.userId())
              .build();
        }
            
        public static DailyMetabolicActivity toDto(RemoveDailyMetabolicActivityRequest request) {
          return DailyMetabolicActivity.builder()
              .id(request.id())
              .build();
        }
      
      
        public static DailyMetabolicActivityResponse toResponse(DailyMetabolicActivity dto) {
          return DailyMetabolicActivityResponse.builder()
              .id(dto.id())
              .userId(dto.userId())
              .date(dto.date())
              .fat(dto.fat())
              .carb(dto.carb())
              .protein(dto.protein())
              .fiber(dto.fiber())
              .water(dto.water())
              .sleep(dto.sleep())
              .build();
        }
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Dto mapper class generated and formatted as expected")
  public void generateDtoMapperClass() {
    DtoMapperTemplate template = new DtoMapperTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
