package co.edu.eci.ecoappetite.server.domain.dto;

import co.edu.eci.ecoappetite.server.domain.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private Role role;
}
