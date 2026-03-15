package org.introspection.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "habitlogs")
public class HabitLog {
    @Id
    private String id;
    private String habitId;
    private String userId;
    private Date date;
    private boolean completed;
    private Mood moodPostHabit;
}
