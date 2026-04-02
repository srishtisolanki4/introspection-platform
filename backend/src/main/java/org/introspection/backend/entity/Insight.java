package org.introspection.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="insights")
public class Insight {
    @Id
    private String id;
    private String userId;
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private String summaryText;
    private Mood dominantMood;
    private double avgStressLevel;
    private double habitCompRate;
    private Map<String,Double> correlation;
    private List<String> topStressTrigger;

}
