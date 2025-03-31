package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.entity.PlatilloEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;

@Mapper(componentModel = "spring")
public interface PlatilloMapper {
    
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nitRestaurante", source = "nitRestaurante")
    //DTO - Modelo
    Platillo toDomain(PlatilloDTO platilloDTO);
    //Modelo - Entidad
    PlatilloEntidad toEntity(Platillo platillo);

    //Entidad - Modelo
    Platillo toDomain(PlatilloEntidad platilloEntidad);
    //Modelo - DTO
    PlatilloDTO toDTO(Platillo platillo);
    
}
