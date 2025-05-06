package co.edu.eci.ecoappetite.server.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {

    private String consumidorId; // ID del consumidor dueño del carrito
    private List<String> productos; // Lista de IDs de productos en el carrito
    private double total; // Precio total del carrito
}
