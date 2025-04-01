package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.model.User;
import co.edu.eci.ecoappetite.server.domain.model.Role;
import co.edu.eci.ecoappetite.server.repository.UserRepository;
import co.edu.eci.ecoappetite.server.domain.dto.AuthResponse;
import co.edu.eci.ecoappetite.server.domain.dto.LoginRequest;
import co.edu.eci.ecoappetite.server.domain.dto.RegisterRequest;
import co.edu.eci.ecoappetite.server.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación que maneja el registro y la autenticación de usuarios.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en la base de datos.
     * 
     * @param request Datos del usuario a registrar.
     * @return AuthResponse con el token de autenticación.
     */
    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtUtils.generateToken(user);

        return new AuthResponse(token);
    }

    /**
     * Inicia sesión autenticando al usuario y generando un token JWT.
     * 
* @param request Datos del usuario para autenticarse.
     * @return AuthResponse con el token generado.
     */
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtUtils.generateToken(user);

        return new AuthResponse(token);
    }
}
