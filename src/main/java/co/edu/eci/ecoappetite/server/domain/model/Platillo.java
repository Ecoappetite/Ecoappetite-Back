package co.edu.eci.ecoappetite.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Platillo {

    private String id;

    @NonNull
    private String nombre;

    @NonNull
    private Double precioOriginal;

    @NonNull
    private Double precioDescuento;

    @NonNull
    private CategoriaPlatillo categoriaPlatillo;

    @NonNull
    private EstadoPlatillo estadoPlatillo;

    @NonNull
    private Integer cantidadDisponible;

    @NonNull
    private String imagen;

    @NonNull
    private String descripcion;

    @NonNull
    private String nitRestaurante;
    
}
