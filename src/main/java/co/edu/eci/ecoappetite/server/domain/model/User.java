package co.edu.eci.ecoappetite.server.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entidad User que representa a los usuarios en la base de datos.
 */
@Document
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Genera automáticamente un UUID
    private UUID id;

    /**
     * Nombre de usuario (único en la base de datos).
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Correo electrónico del usuario (único).
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Contraseña encriptada del usuario.
     */
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    /**
     * Indica si la cuenta está habilitada.
     */
    @Column(nullable = false)
    private boolean isEnabled = true;

    /**
     * Indica si la cuenta está bloqueada.
     */
    @Column(nullable = false)
    private boolean isAccountNonLocked = true;

    /**
     * Fecha de creación del usuario.
     */
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha de última actualización del usuario.
     */
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User(String username, String encode) {
    }

    /**
     * Se ejecuta antes de insertar un nuevo usuario en la base de datos.
     */
    @PrePersist
    public void prePersist() {
        id = UUID.randomUUID();
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * Se ejecuta antes de actualizar un usuario en la base de datos.
     */
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

