package co.edu.eci.ecoappetite.server.domain.dto;

import lombok.Data;

@Data
public class CheckoutResponse {
    private String preferenceId;
    private String initPoint;
    private String sandboxInitPoint;
}