package org.introspection.backend.repository;

import org.introspection.backend.entity.HabitLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HabitLogRepository extends MongoRepository<HabitLog,String> {

    Optional<HabitLog> findByUserIdAndHabitIdAndDate(String userId, String habitId, LocalDate date);
    List<HabitLog> findByUserIdAndDateBetween(
            String userId,
            LocalDate start,
            LocalDate end
    );
    List<HabitLog> findByUserIdAndHabitIdOrderByDateDesc(String userId,String habitId);
    List<HabitLog> findByUserIdAndDate(String userId, LocalDate date);
}
