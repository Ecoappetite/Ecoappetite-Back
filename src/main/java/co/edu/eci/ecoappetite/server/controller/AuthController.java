package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.dto.LoginRequest;
import co.edu.eci.ecoappetite.server.dto.RegisterRequest;
import co.edu.eci.ecoappetite.server.dto.AuthResponse;
import co.edu.eci.ecoappetite.server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * Controlador de autenticación que maneja el registro y el login de usuarios.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para el registro de un nuevo usuario.
     *
     * @param request Datos del usuario a registrar.
     * @return ResponseEntity con el usuario creado.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.created(URI.create("/auth/signup")).body(response); // Crea un registro del Token cuando el  usuario finaliza la sesión
    }

    /**
     * Endpoint para el inicio de sesión de un usuario.
     *
     * @param request Datos del usuario para autenticarse.
     * @return ResponseEntity con el token de autenticación.
     */
    @PostMapping("/login") // Genera el Token y valida la firma electronica de cada token
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

