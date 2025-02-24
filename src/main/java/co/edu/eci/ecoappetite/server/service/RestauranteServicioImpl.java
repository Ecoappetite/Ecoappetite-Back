package co.edu.eci.ecoappetite.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;

@Service
public class RestauranteServicioImpl implements RestauranteServicio {

    private final RestauranteRepositorio restauranteRepositorio;
    private final RestauranteMapper restauranteMapper;

    @Autowired
    public RestauranteServicioImpl(RestauranteRepositorio restauranteRepositorio, RestauranteMapper restauranteMapper){
        this.restauranteRepositorio = restauranteRepositorio;
        this.restauranteMapper = restauranteMapper;

    }

    @Override
    public RestauranteDTO registrarRestaurante(RestauranteDTO restauranteDTO) throws EcoappetiteException {
        Restaurante restaurante = restauranteMapper.toDomain(restauranteDTO);
        Restaurante nuevoRestaurante = restauranteRepositorio.registrarRestaurante(restaurante);
        return restauranteMapper.toDTO(nuevoRestaurante);
    }


    
}
