package org.introspection.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="habits")
public class Habit {
    @Id
    private String id;
    private String userId;
    private String name;
    private Integer targetPerWeek;;
    private String description;
    @CreatedDate
    private LocalDate createdAt;
}
