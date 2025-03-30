package co.edu.eci.ecoappetite.server.config.security;

import co.edu.eci.ecoappetite.server.config.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase de configuración de seguridad para la aplicación.
 * Se encarga de definir las reglas de seguridad, incluyendo la autenticación JWT y la gestión de sesiones.
 */
@Configuration // Indica que esta clase es una configuración de Spring
@EnableWebSecurity // Habilita la configuración de seguridad en Spring
@RequiredArgsConstructor // Genera automáticamente un constructor con los atributos finales
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter; // Filtro que valida los tokens JWT
    private final UserDetailsService userDetailsService; // Servicio que carga los detalles del usuario desde la BD

    /**
     * Configura la cadena de filtros de seguridad.
     * 
     * @param http Configuración de seguridad HTTP de Spring Security
     * @return SecurityFilterChain con las configuraciones aplicadas
     * @throws Exception en caso de errores en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Deshabilita CSRF porque se usa JWT en lugar de sesiones y cookies
                .csrf(csrf -> csrf.disable())
                
                // Configura la gestión de sesiones para que sea sin estado (stateless), ya que se usa JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura la autorización de peticiones HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permite acceso sin autenticación a los endpoints de login y registro
                        .requestMatchers("/auth/login", "/auth/signup").permitAll()
                        
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )

                // Agrega el filtro JWT antes del filtro de autenticación de usuario y contraseña
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                
                // Construye la configuración de seguridad
                .build();
    }

    /**
     * Configura el gestor de autenticación.
     * Define un proveedor de autenticación basado en DAO que usa `UserDetailsService` y `BCryptPasswordEncoder`.
     *
     * @return AuthenticationManager para gestionar la autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Usa el servicio de usuarios para autenticación
        authProvider.setPasswordEncoder(passwordEncoder()); // Usa BCrypt para cifrar contraseñas
        return new ProviderManager(authProvider);
    }

    /**
     * Define el mecanismo de codificación de contraseñas con BCrypt.
     * 
     * @return Un `PasswordEncoder` basado en `BCryptPasswordEncoder`
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

