package org.introspection.backend.repository;

import org.introspection.backend.entity.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EntryRepository extends MongoRepository<Entry,String> {
    List<Entry> findByUserId(String userId);
    List<Entry> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime start, LocalDateTime end);
}
