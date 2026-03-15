package org.introspection.backend.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="entries")
public class Entry {
    @Id
    private String id;
    private String userId;
    private String title;
    private String content;

    private Mood mood;
    private int energyLevel;
    private int productivityScore;
    private int sleepHours;
    private int stressLevel;

    private List<String> activities;
    private List<String> tags;
    private LocalDateTime createdAt;

}
