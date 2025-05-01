package co.edu.eci.ecoappetite.server.domain.dto.recomendacion;

import com.mongodb.lang.NonNull;

import co.edu.eci.ecoappetite.server.domain.model.CategoriaPlatillo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatilloRecomendacionDTO {

    private String id;

    @NonNull
    private String nombre;

    private Double precioOriginal;

    @NonNull
    private Double precioDescuento;

    private CategoriaPlatillo categoriaPlatillo;
    private String descripcion;
    
}
