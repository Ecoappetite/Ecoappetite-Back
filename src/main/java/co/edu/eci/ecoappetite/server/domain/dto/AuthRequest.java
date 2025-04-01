package co.edu.eci.ecoappetite.server.domain.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
