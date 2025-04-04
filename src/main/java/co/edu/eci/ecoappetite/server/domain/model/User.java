package co.edu.eci.ecoappetite.server.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    private Set<Role> roles = new HashSet<>();

    private boolean isEnabled = true;
    private boolean isAccountNonLocked = true;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructor útil para crear el usuario en AuthController
     */
    public User(String username, String encodedPassword) {
        this.username = username;
        this.password = encodedPassword;
    }

    /**
     * Se ejecuta antes de insertar un nuevo usuario en la base de datos.
     */
    public void prePersist() {
        if (id == null) id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Se ejecuta antes de actualizar un usuario en la base de datos.
     */
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}


