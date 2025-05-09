package co.edu.eci.ecoappetite.server.config.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.service.AlmacenTokenServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final AlmacenTokenServicio almacenTokenServicio;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && this.verificarToken(token)){

            try {
                String username = almacenTokenServicio.obtenerSubject(token);
                Rol rol = almacenTokenServicio.obtenerRol(token);
    
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol.name()));
    
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
    
                SecurityContextHolder.getContext().setAuthentication(authToken);
                
            } catch (DataValidationException e) {
                log.error("Error al procesar el token JWT: {}", e.getMessage());
  
            } 
        }
        filterChain.doFilter(request, response);
    }

    private Boolean verificarToken(String token) {
        try {
            return almacenTokenServicio.verificacionToken(token);
        } catch (DataValidationException e) {
            return false;
        }
    }    
}
