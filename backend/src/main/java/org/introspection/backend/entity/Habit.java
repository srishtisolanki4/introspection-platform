package org.introspection.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="habits")
public class Habit {
    @Id
    private String id;
    private String userId;
    private String habitName;
    private String freq;
    private String goalDescription;
    private LocalDateTime createdAt;
}
