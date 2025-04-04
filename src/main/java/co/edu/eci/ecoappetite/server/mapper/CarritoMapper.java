package co.edu.eci.ecoappetite.server.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import co.edu.eci.ecoappetite.server.domain.dto.CarritoDTO;
import co.edu.eci.ecoappetite.server.domain.entity.CarritoEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Carrito;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface CarritoMapper {

    CarritoMapper INSTANCE = Mappers.getMapper(CarritoMapper.class);

    Carrito toDomain(CarritoDTO carritoDTO);

    CarritoDTO toDTO(Carrito carrito);

    // Mapear de CarritoEntidad a Carrito, asegurando que las listas no sean nulas
    @Mapping(target = "items", expression = "java(carritoEntidad.getProductos() != null ? new ArrayList<>() : new ArrayList<>())")
    @Mapping(target = "productos", expression = "java(carritoEntidad.getProductos() != null ? carritoEntidad.getProductos() : new ArrayList<>())")
    Carrito toDomain(CarritoEntidad carritoEntidad);

    // Mapear de Carrito a CarritoEntidad, asegurando que el ID sea el consumidorId
    @Mapping(target = "consumidorId", source = "consumidorId")
    @Mapping(target = "productos", source = "productos")
    CarritoEntidad toEntity(Carrito carrito);
}
