package org.introspection.backend.repository;

import org.introspection.backend.entity.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface EntryRepository extends MongoRepository<Entry,String> {
    List<Entry> findByUserId(String userId);
    List<Entry> findByUserIdAndDateBetween(String userId, LocalDate weekStart, LocalDate weekEnd);
}
