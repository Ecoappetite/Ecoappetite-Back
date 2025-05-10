package co.edu.eci.ecoappetite.server.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Usuario {

    private String correo;
    private String contrasena;
    private Rol rol;
    private String idEntidadAlmacenada;

}
