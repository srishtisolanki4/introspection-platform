package org.introspection.backend.dto;


import lombok.Builder;
import lombok.Data;
import org.introspection.backend.entity.Mood;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EntryResponseDTO {
    private String id;
    private String title;
    private String content;
    private Mood mood;
    private Integer productivityScore;
    private Integer sleepHours;
    private Integer stressLevel;
    private LocalDate date;
    private List<String> activities;
    private List<String> tags;
    private Integer energyLevel;
    private LocalDate createdAt;

}
