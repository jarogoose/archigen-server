package org.jarogoose.archigen.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UNIT TEST] Request template generation")
class RequestTemplateTest {

  @Test
  @DisplayName("[FEATURE] Add daily metabolic activity request class generated and formatted as expected")
  public void generateAddDailyMetabolicActivityRequestClass() {
    final String expected = """
        package com.jarogoose.enenbi.feature.metabolism.domain.model.request;
        
        import com.fasterxml.jackson.annotation.JsonProperty;
        import lombok.Builder;
        
        @Builder
        public record AddDailyMetabolicActivityRequest(
            @JsonProperty("userId") String userId,
            @JsonProperty("date") String date,
            @JsonProperty("fat") String fat,
            @JsonProperty("carb") String carb,
            @JsonProperty("protein") String protein,
            @JsonProperty("fiber") String fiber,
            @JsonProperty("water") String water,
            @JsonProperty("sleep") String sleep
        ) {
        
        }
        
        """;

    RequestTemplate template = new RequestTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain(), Given.addRequest());

    Then.validTemplate(actual, expected);
  }

  @Test
  @DisplayName("[FEATURE] Modify daily metabolic activity request class generated and formatted as expected")
  public void generateModifyDailyMetabolicActivityRequestClass() {
    final String expected = """
        package com.jarogoose.enenbi.feature.metabolism.domain.model.request;
        
        import com.fasterxml.jackson.annotation.JsonProperty;
        import lombok.Builder;
        
        @Builder
        public record ModifyDailyMetabolicActivityRequest(
            @JsonProperty("id") String id,
            @JsonProperty("userId") String userId,
            @JsonProperty("date") String date,
            @JsonProperty("fat") String fat,
            @JsonProperty("carb") String carb,
            @JsonProperty("protein") String protein,
            @JsonProperty("fiber") String fiber,
            @JsonProperty("water") String water,
            @JsonProperty("sleep") String sleep
        ) {
        
        }
        
        """;

    RequestTemplate template = new RequestTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain(), Given.modifyRequest());

    Then.validTemplate(actual, expected);
  }

  @Test
  @DisplayName("[FEATURE] Search daily metabolic activity request class generated and formatted as expected")
  public void generateSearchDailyMetabolicActivityRequestClass() {
    final String expected = """
        package com.jarogoose.enenbi.feature.metabolism.domain.model.request;
        
        import com.fasterxml.jackson.annotation.JsonProperty;
        import lombok.Builder;
        
        @Builder
        public record SearchDailyMetabolicActivityRequest(
            @JsonProperty("id") String id
        ) {
        
        }
        
        """;

    RequestTemplate template = new RequestTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain(), Given.searchRequest());

    Then.validTemplate(actual, expected);
  }

  @Test
  @DisplayName("[FEATURE] Show all daily metabolic activity request class generated and formatted as expected")
  public void generateShowAllDailyMetabolicActivitiesRequestClass() {
    final String expected = """
        package com.jarogoose.enenbi.feature.metabolism.domain.model.request;
        
        import com.fasterxml.jackson.annotation.JsonProperty;
        import lombok.Builder;
        
        @Builder
        public record ShowAllDailyMetabolicActivitiesRequest(
            @JsonProperty("userId") String userId
        ) {
        
        }
        
        """;

    RequestTemplate template = new RequestTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain(), Given.showAllRequest());

    Then.validTemplate(actual, expected);
  }

  @Test
  @DisplayName("[FEATURE] Remove daily metabolic activity request class generated and formatted as expected")
  public void generateRemoveDailyMetabolicActivityRequestClass() {
    final String expected = """
        package com.jarogoose.enenbi.feature.metabolism.domain.model.request;
        
        import com.fasterxml.jackson.annotation.JsonProperty;
        import lombok.Builder;
        
        @Builder
        public record RemoveDailyMetabolicActivityRequest(
            @JsonProperty("id") String id
        ) {
        
        }
        
        """;

    RequestTemplate template = new RequestTemplate();
    String actual = template.createTemplate(Given.dailyMetabolicActivityDomain(), Given.removeRequest());

    Then.validTemplate(actual, expected);
  }
}