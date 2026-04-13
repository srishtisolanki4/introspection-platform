package org.introspection.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.introspection.backend.entity.Mood;

import java.time.LocalDate;
import java.util.List;

@Data
public class EntryRequestDTO {
    @NotBlank
    private String title;
    private String content;

    private Mood mood;
    @Min(1) @Max(10)
    private Integer energyLevel;
    private Integer productivityScore;
    private Integer sleepHours;
    @Min(1) @Max(10)
    private Integer stressLevel;
    private LocalDate date;
    private List<String> activities;
    private List<String> tags;
}
