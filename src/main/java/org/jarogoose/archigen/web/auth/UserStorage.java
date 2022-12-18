package org.jarogoose.archigen.web.auth;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserStorage extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

}
