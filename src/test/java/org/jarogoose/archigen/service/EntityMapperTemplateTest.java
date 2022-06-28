package org.jarogoose.archigen.service;

import org.jarogoose.archigen.service.gwt.Given;
import org.jarogoose.archigen.service.gwt.Then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Entity mapper template generation")
class EntityMapperTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.storage;
      
      import com.jarogoose.enenbi.feature.metabolism.domain.model.dto.DailyMetabolicActivity;
      import java.util.ArrayList;
      import java.util.List;
      
      class DailyMetabolicActivityEntityMapper {
      
        public static DailyMetabolicActivityEntity toEntity(DailyMetabolicActivity dto) {
          return DailyMetabolicActivityEntity.builder()
              .id(dto.id() == null ? null : dto.id())
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
      
        public static DailyMetabolicActivity toDto(DailyMetabolicActivityEntity entity) {
          return DailyMetabolicActivity.builder()
              .id(entity.getId())
              .userId(entity.getUserId())
              .date(entity.getDate())
              .fat(entity.getFat())
              .carb(entity.getCarb())
              .protein(entity.getProtein())
              .fiber(entity.getFiber())
              .water(entity.getWater())
              .sleep(entity.getSleep())
              .build();
        }
      
        public static List<DailyMetabolicActivity> toDto(List<DailyMetabolicActivityEntity> entities) {
          List<DailyMetabolicActivity> dtos = new ArrayList<>();
          for (DailyMetabolicActivityEntity entity : entities) {
            dtos.add(toDto(entity));
          }
          return dtos;
        }
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Entity mapper class generated and formatted as expected")
  public void generateEntityMapperClass() {
    EntityMapperTemplate template = new EntityMapperTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
