package co.edu.eci.ecoappetite.server.controller.security;

import co.edu.eci.ecoappetite.server.UserDetailsImpl;
import co.edu.eci.ecoappetite.server.config.JwtUtils;
import co.edu.eci.ecoappetite.server.domain.dto.JwtResponse;
import co.edu.eci.ecoappetite.server.domain.dto.LoginRequest;
import co.edu.eci.ecoappetite.server.domain.dto.MessageResponse;
import co.edu.eci.ecoappetite.server.domain.dto.SignupRequest;
import co.edu.eci.ecoappetite.server.domain.model.Role;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import co.edu.eci.ecoappetite.server.domain.model.User;
import co.edu.eci.ecoappetite.server.repository.RolRepository;
import co.edu.eci.ecoappetite.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (usuarioRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: ¡El nombre de usuario ya está en uso!"));
        }
    
        // Crear nueva cuenta de usuario
        User usuario = new User();
        usuario.setUsername(signUpRequest.getUsername());
        usuario.setEmail(signUpRequest.getEmail());
        usuario.setPassword(encoder.encode(signUpRequest.getPassword()));
    
        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();
    
        if (strRoles == null || strRoles.isEmpty()) {
            roles.add(Role.CONSUMIDOR);
        } else {
            strRoles.forEach(role -> {
                if (role.equalsIgnoreCase("RESTAURANTE")) {
                    roles.add(Role.RESTAURANTE);
                } else if (role.equalsIgnoreCase("CONSUMIDOR")) {
                    roles.add(Role.CONSUMIDOR);
                } else {
                    throw new RuntimeException("Rol inválido: " + role);
                }
            });
        }
    
        usuario.setRoles(roles);
        usuarioRepository.save(usuario);
    
        return ResponseEntity.ok(new MessageResponse("¡Usuario registrado exitosamente!"));
    }    
}

