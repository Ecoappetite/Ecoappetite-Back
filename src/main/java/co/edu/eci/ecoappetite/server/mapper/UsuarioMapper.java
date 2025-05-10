package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;

import co.edu.eci.ecoappetite.server.domain.entity.UsuarioEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioEntidad toEntity(Usuario usuario);
    Usuario toDomain(UsuarioEntidad usuarioEntidad);
    
}
