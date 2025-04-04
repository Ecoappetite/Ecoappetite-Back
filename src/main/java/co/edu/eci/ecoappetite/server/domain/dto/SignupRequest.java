package co.edu.eci.ecoappetite.server.domain.dto;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import co.edu.eci.ecoappetite.server.domain.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
