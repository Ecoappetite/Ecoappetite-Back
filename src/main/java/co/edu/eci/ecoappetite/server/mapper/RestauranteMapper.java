package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.dto.recomendacion.RestauranteRecomendacionDTO;
import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;

@Mapper(componentModel = "spring")
public interface RestauranteMapper {
    Restaurante toDomain(RestauranteDTO restauranteDTO);
    RestauranteEntidad toEntity(Restaurante restaurante);

    Restaurante toDomain(RestauranteEntidad restauranteEntidad);
    RestauranteDTO toDTO(Restaurante restaurante);

    //Recomendacion
    @Mapping(source="platillos", target="platillosRecomendacion")
    RestauranteRecomendacionDTO toRecomendacion(RestauranteDTO restauranteDTO);
    
}
