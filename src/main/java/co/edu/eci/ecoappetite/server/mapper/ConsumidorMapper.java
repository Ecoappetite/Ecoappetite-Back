package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;

@Mapper(componentModel = "spring")
public interface ConsumidorMapper {
    //Convierte entre DTO, entidad y modelo.
    Consumidor toDomain(ConsumidorDTO consumidorDTO);
    ConsumidorEntidad toEntity(Consumidor consumidor);
    Consumidor toDomain(ConsumidorEntidad consumidorEntidad);
    ConsumidorDTO toDTO(Consumidor consumidor);

}
