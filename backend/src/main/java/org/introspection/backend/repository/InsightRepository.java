package org.introspection.backend.repository;


import org.introspection.backend.entity.Insight;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface InsightRepository extends MongoRepository<Insight,String>  {
    Optional<Insight> findByUserIdAndWeekStart(String userId, LocalDate weekStart);
}
