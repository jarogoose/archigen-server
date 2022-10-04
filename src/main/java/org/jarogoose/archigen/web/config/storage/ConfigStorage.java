package org.jarogoose.archigen.web.config.storage;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigStorage extends MongoRepository<ConfigEntity, String> {
  Optional<ConfigEntity> findByProjectName(String projectName);
}
