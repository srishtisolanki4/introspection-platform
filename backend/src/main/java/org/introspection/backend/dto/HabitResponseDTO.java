package org.introspection.backend.dto;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Builder
public class HabitResponseDTO {
    private String id;
    private String name;
    private Integer targetPerWeek;;
    private String description;
    private LocalDateTime createdAt;


}
