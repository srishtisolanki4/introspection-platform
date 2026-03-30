package org.introspection.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data

public class HabitRequestDTO {
    private String name;
    private String description;
    private Integer targetPerWeek;
}
