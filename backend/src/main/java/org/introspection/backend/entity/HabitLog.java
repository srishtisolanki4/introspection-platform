package org.introspection.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habitlogs")
@CompoundIndex(
        name = "unique_habit_log",
        def = "{'userId': 1, 'habitId': 1, 'date': 1}",
        unique = true
)

public class HabitLog {
    @Id
    private String id;
    private String habitId;
    private String userId;
    private LocalDate date;
    @CreatedDate
    private LocalDate createdAt;
    private boolean completed;
    private Mood moodPostHabit;
}
