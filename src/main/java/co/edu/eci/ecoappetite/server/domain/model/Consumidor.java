
package co.edu.eci.ecoappetite.server.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consumidor {
    //El modelo represent el objeto de negocio en la app
    @NonNull
    private String id;
    @NonNull
    private String nombre;
    @NonNull
    private String email;
    private String telefono;
    private String direccion;
    private String preferencias;
}