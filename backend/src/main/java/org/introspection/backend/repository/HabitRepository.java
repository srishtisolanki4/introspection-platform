package org.introspection.backend.repository;


import org.introspection.backend.entity.Habit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HabitRepository extends MongoRepository<Habit,String> {
}
