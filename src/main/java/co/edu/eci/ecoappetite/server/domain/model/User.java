package co.edu.eci.ecoappetite.server.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Entidad User que representa a los usuarios en la base de datos.
 */
@Entity
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Nombre de usuario (único en la base de datos).
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Contraseña encriptada del usuario.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Rol del usuario (ADMIN, USER, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
