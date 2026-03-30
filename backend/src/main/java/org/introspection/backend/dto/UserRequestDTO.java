package org.introspection.backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-z0-9_,.]+$" ,message = "Username must not contain spaces")
    private String userName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}
