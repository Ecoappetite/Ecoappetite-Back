package co.edu.eci.ecoappetite.server.domain.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.eci.ecoappetite.server.domain.model.CategoriaPlatillo;
import co.edu.eci.ecoappetite.server.domain.model.EstadoPlatillo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "platillos")
public class PlatilloEntidad {
    
    @Id
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
