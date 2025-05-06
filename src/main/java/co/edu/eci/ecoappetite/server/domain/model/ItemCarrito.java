package co.edu.eci.ecoappetite.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {
    private String nombre;
    private double precio;
}
