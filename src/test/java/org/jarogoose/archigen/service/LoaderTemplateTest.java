package org.jarogoose.archigen.service;

import org.jarogoose.archigen.service.gwt.Given;
import org.jarogoose.archigen.service.gwt.Then;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Loader template generation")
class LoaderTemplateTest {

  private static final String EXPECTED = """
      package com.jarogoose.enenbi.feature.metabolism.storage;
      
      import java.util.List;
      import lombok.extern.slf4j.Slf4j;
      import com.jarogoose.enenbi.feature.metabolism.domain.model.dto.DailyMetabolicActivity;
      import static com.jarogoose.enenbi.feature.metabolism.storage.DailyMetabolicActivityEntityMapper.toDto;
      import static com.jarogoose.enenbi.feature.metabolism.storage.DailyMetabolicActivityEntityMapper.toEntity;
      import com.jarogoose.enenbi.feature.metabolism.domain.exception.DailyMetabolicActivityNotFoundException;
      import org.springframework.stereotype.Repository;
      
      @Slf4j
      @Repository
      public class DailyMetabolicActivityLoader {
      
        private final DailyMetabolicActivityStorage storage;
      
        public DailyMetabolicActivityLoader(DailyMetabolicActivityStorage storage) {
          this.storage = storage;
        }
      
        public void save(DailyMetabolicActivity dto) {
          storage.save(toEntity(dto));
        }
      
        public void update(DailyMetabolicActivity dto) {
          storage.save(toEntity(dto));
        }
      
        public DailyMetabolicActivity findById(String id) {
          return storage.findById(id)
              .map(DailyMetabolicActivityEntityMapper::toDto)
              .orElseThrow(() -> new DailyMetabolicActivityNotFoundException(
                  "[METABOLISM] daily metabolic activity was not found"));
        }
      
        public List<DailyMetabolicActivity> findAllByUserId(String userId) {
          return toDto(storage.findAllByUserId(userId));
        }
      
        public void deleteById(String id) {
          storage.deleteById(id);
        }
      
      }
      
      """;

  @Test
  @DisplayName("[FEATURE] Loader class generated and formatted as expected")
  public void generateLoaderClass() {
    LoaderTemplate template = new LoaderTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain());

    Then.validTemplate(actual, EXPECTED);
  }
}
