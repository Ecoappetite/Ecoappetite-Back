package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;

import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {
    Restaurante toDomain(RestauranteDTO restauranteDTO);
    RestauranteEntidad toEntity(Restaurante restaurante);

    Restaurante toDomain(RestauranteEntidad restauranteEntidad);
    RestauranteDTO toDTO(Restaurante restaurante);
    
}
