package org.introspection.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="insights")
public class Insight {
    @Id
    private String id;
    private String userId;
    private LocalDateTime weekStart;
    private LocalDateTime weekEnd;

    private String summaryText;
    private Mood dominantMood;
    private double avgStressLevel;
    private double habitCompRate;
    private Map<String,Double> correlation;
    private List<String> topStressTrigger;

}
