package org.introspection.backend.repository;


import org.introspection.backend.entity.Insight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InsightRepository extends MongoRepository<Insight,String>  {
}
