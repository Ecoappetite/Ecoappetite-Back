package co.edu.eci.ecoappetite.server.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeración que define los roles disponibles en el sistema.
 */
public enum Role {
    ADMIN, // Rol para administradores del sistema
    USER,// Rol estándar para usuarios normales
    CONSUMIDOR,
    RESTAURANTE;



    @JsonCreator
    public static Role fromString(String value) {
        return Role.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.name();
    }
}
