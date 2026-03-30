package org.introspection.backend.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private String userName;
    private String email;
    private LocalDateTime createdAt;
}
