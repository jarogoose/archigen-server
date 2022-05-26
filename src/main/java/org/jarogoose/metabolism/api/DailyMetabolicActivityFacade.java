package org.jarogoose.metabolism.api;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jarogoose.metabolism.domain.model.response.DailyMetabolicActivityResponse;
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

public DailyMetabolicActivityResponse showAllDailyMetabolicActivities(ShowAllDailyMetabolicActivitiesRequest request) {
  return toResponse(service.showAll(toDto(request)));
}

public void removeDailyMetabolicActivity(RemoveDailyMetabolicActivityRequest request) {
  service.remove(toDto(request));
}


}
