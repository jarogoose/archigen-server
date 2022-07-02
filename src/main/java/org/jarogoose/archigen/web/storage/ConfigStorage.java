package org.jarogoose.archigen.web.storage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigStorage extends MongoRepository<ConfigEntity, String> {

}
