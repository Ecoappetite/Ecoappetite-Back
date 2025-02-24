package co.edu.eci.ecoappetite.server.domain.dto;

import co.edu.eci.ecoappetite.server.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteDTO {
    
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
    private String whatsapp;
    private Categoria categoria;
    private String imagen;
    private String descripcion;
    
}
