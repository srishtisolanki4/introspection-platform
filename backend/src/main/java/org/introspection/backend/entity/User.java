package org.introspection.backend.entity;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")

public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username must not contain spaces")
    private String userName;
    @Indexed(unique = true)
    private String email;
    @NonNull
    private String password;
    @CreatedDate
    private LocalDate createdAt;
}
