package co.edu.eci.ecoappetite.server.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "usuarios")
public class UsuarioEntidad {

    @Id
    private String correo;
    private String contrasena;
    private Rol rol;
    private String idEntidadAlmacenada;
    
}
