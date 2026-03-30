package org.introspection.backend.dto;

import lombok.Data;
import org.introspection.backend.entity.Mood;

import java.util.List;

@Data
public class EntryRequestDTO {
    private String title;
    private String content;

    private Mood mood;
    private Integer energyLevel;
    private Integer productivityScore;
    private Integer sleepHours;
    private Integer stressLevel;

    private List<String> activities;
    private List<String> tags;
}
