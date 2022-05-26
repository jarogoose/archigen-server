package org.jarogoose.metabolism.control;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
@RequestMapping("metabolism-api")
public class DailyMetabolicActivityController {

private final DailyMetabolicActivityFacade facade;

public DailyMetabolicActivityController(DailyMetabolicActivityFacade facade) {
  this.facade = facade;
}


@PostMapping("add-daily-metabolic-activity")
public ResponseEntity<Object> addDailyMetabolicActivity(AddDailyMetabolicActivityRequest request) {
  try {
    DailyMetabolicActivity dailyMetabolicActivity = facade.addDailyMetabolicActivity(request);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  } catch (DailyMetabolicActivityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}
@PutMapping("modify-daily-metabolic-activity")
public ResponseEntity<Object> modifyDailyMetabolicActivity(ModifyDailyMetabolicActivityRequest request) {
  try {
    DailyMetabolicActivity dailyMetabolicActivity = facade.modifyDailyMetabolicActivity(request);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  } catch (DailyMetabolicActivityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}
@GetMapping("search-daily-metabolic-activity")
public ResponseEntity<Object> searchDailyMetabolicActivity(SearchDailyMetabolicActivityRequest request) {
  try {
    DailyMetabolicActivity dailyMetabolicActivity = facade.searchDailyMetabolicActivity(request);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  } catch (DailyMetabolicActivityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}
@GetMapping("show-all-daily-metabolic-activities")
public ResponseEntity<Object> showAllDailyMetabolicActivities(ShowAllDailyMetabolicActivitiesRequest request) {
  try {
    DailyMetabolicActivity dailyMetabolicActivity = facade.showAllDailyMetabolicActivities(request);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  } catch (DailyMetabolicActivityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}
@DeleteMapping("remove-daily-metabolic-activity")
public ResponseEntity<Object> removeDailyMetabolicActivity(RemoveDailyMetabolicActivityRequest request) {
  try {
    DailyMetabolicActivity dailyMetabolicActivity = facade.removeDailyMetabolicActivity(request);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  } catch (DailyMetabolicActivityNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}

}
