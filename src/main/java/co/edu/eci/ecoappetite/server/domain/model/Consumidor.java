package co.edu.eci.ecoappetite.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Consumidor {
    //El modelo represent el objeto de negocio en la app
    @NonNull
    private String id;
    @NonNull
    private String nombre;
    @NonNull
    private String email;
    @NonNull
    private String telefono;
    private String direccion;
    private String preferencias;
}



