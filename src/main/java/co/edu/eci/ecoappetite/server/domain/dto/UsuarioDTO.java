package co.edu.eci.ecoappetite.server.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private String correo;
    private String contrasena;

    @JsonIgnore
    private Rol rol;

    @JsonIgnore
    private String idEntidadAlmacenada;
    
}
