package org.introspection.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Builder

public class InsightResponseDTO {
    private LocalDate weekStart;
    private LocalDate weekEnd;

    private String dominantMood;

    private double avgStressLevel;

    private double habitCompletionRate;

    private Map<String, Double> correlation;

    private List<String> topStressTriggers;

    private String summaryText;

}
