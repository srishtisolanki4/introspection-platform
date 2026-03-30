package org.introspection.backend.repository;


import org.introspection.backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String>{
     Optional<User> findByUserName(String userName);
     Optional<User> findByEmail (String email);
     boolean existsByUserName(String username);
     void deleteByEmail(String email);
}

