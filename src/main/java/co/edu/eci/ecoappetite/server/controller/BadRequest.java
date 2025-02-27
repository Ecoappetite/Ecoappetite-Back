package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BadRequest {
    public static ResponseEntity<?> manejarErrores(Exception e) {
        if (e instanceof EcoappetiteException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
    }
}
