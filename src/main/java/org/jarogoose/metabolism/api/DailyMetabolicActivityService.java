package org.jarogoose.metabolism.api;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
class DailyMetabolicActivityService {

private final DailyMetabolicActivityLoader loader;

public DailyMetabolicActivityService(DailyMetabolicActivityLoader loader) {
  this.loader = loader;
}


public void add(DailyMetabolicActivity dto) {
  loader.save(dto);
}

public void modify(DailyMetabolicActivity dto) {
  loader.update(dto);
}

public DailyMetabolicActivity search(DailyMetabolicActivity dto) {
  return loader.findById(dto);
}

public DailyMetabolicActivity showAll(DailyMetabolicActivity dto) {
  return loader.findAllByUserId(dto);
}

public void remove(DailyMetabolicActivity dto) {
  loader.deleteById(dto);
}


}
