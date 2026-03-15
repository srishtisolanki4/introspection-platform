package org.introspection.backend.repository;

import org.introspection.backend.entity.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<Entry,String>  {
}
