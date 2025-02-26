package co.edu.eci.ecoappetite.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Restaurante {
    
    @NonNull
    private String nit;

    @NonNull
    private String nombre;

    @NonNull
    private String direccion;

    @NonNull
    private String telefono;

    private String whatsapp;

    @NonNull
    private Categoria categoria;

    @NonNull
    private String imagen;

    private String descripcion;




    
}
