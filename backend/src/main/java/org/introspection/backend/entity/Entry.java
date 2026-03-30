package org.introspection.backend.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
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
    @NonNull
    private String title;
    private String content;

    private Mood mood;
    private Integer energyLevel;
    private Integer productivityScore;
    private Integer sleepHours;
    private Integer stressLevel;

    private List<String> activities;
    private List<String> tags;

    @CreatedDate
    private LocalDateTime createdAt;

}
