package org.introspection.backend.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
    private String userName;
    private String email;
    private LocalDate createdAt;
}
