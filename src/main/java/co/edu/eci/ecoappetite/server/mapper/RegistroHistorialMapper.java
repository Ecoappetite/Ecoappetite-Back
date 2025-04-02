package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;

import co.edu.eci.ecoappetite.server.domain.entity.RegistroHistorialEntidad;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;



@Mapper(componentModel = "spring")
public interface RegistroHistorialMapper {

    RegistroHistorialEntidad toEntity(RegistroHistorial registroHistorial);
    RegistroHistorial toDomain(RegistroHistorialEntidad registroHistorialEntidad);
    
    
}
