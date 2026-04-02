package org.introspection.backend.dto;

import jakarta.annotation.sql.DataSourceDefinitions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.introspection.backend.entity.Mood;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class HabitTodayResponseDTO {
    private String habitId;
    private String habitName;
    private boolean completed;
    private Mood moodPostHabit;
    private int streak;
}
