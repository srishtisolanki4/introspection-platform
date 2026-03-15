package org.introspection.backend.repository;

import org.introspection.backend.entity.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InsightRepository extends MongoRepository<Entry,String>  {
}
