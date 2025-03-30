package co.edu.eci.ecoappetite.server.config;

import co.edu.eci.ecoappetite.server.service.JwtService;
import co.edu.eci.ecoappetite.server.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filtro de autenticación JWT que intercepta cada solicitud HTTP y valida el token.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructor que inyecta los servicios necesarios.
     * @param jwtService Servicio para manejar JWT.
     * @param userDetailsService Servicio para obtener los detalles del usuario.
     */
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Método que intercepta cada solicitud y valida el JWT.
     * @param request Petición HTTP.
     * @param response Respuesta HTTP.
     * @param filterChain Cadena de filtros de seguridad.
     * @throws ServletException Excepción en caso de error en el servlet.
     * @throws IOException Excepción en caso de error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Obtener el encabezado de autorización
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Si no hay token o no comienza con "Bearer ", continuar con la cadena de filtros
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraer el token eliminando el prefijo "Bearer "
        String jwtToken = authHeader.substring(7);
        String username = jwtService.extractUsername(jwtToken); // Obtener el usuario del token

        // Verificar si el usuario no está autenticado en el contexto de seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Validar el token con los detalles del usuario
            if (jwtService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}

