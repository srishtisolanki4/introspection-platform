package org.introspection.backend.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class EntryResponseDTO {
    private String id;
    private String title;
    private String content;
    private Integer energyLevel;
    private LocalDateTime createdAt;
    private List<String> tags;
}
