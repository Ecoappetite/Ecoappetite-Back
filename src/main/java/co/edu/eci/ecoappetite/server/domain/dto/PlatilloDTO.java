package co.edu.eci.ecoappetite.server.domain.dto;

import co.edu.eci.ecoappetite.server.domain.model.CategoriaPlatillo;
import co.edu.eci.ecoappetite.server.domain.model.EstadoPlatillo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatilloDTO {
    private String id;
    private String nombre;
    private Double precioOriginal;
    private Double precioDescuento;
    private CategoriaPlatillo categoriaPlatillo;
    private EstadoPlatillo estadoPlatillo;
    private Integer cantidadDisponible;
    private String imagen;
    private String descripcion;
    private String nitRestaurante;
    
}
