package org.introspection.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.introspection.backend.entity.Mood;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitLogRequestDTO {
    private String habitId;
    private boolean completed;
    private Mood moodPostHabit;
}
