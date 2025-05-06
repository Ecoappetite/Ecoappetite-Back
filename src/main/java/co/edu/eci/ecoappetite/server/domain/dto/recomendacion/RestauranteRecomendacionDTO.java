package co.edu.eci.ecoappetite.server.domain.dto.recomendacion;

import java.util.Collection;
import co.edu.eci.ecoappetite.server.domain.model.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestauranteRecomendacionDTO {

    private String nit;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private Collection<PlatilloRecomendacionDTO> platillosRecomendacion;
    
}
