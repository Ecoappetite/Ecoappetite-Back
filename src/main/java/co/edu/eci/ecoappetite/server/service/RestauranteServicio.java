package co.edu.eci.ecoappetite.server.service;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RestauranteServicio {

    RestauranteDTO registrarRestaurante(RestauranteDTO restauranteDTO) throws EcoappetiteException;
    List<RestauranteDTO> consultarTodosLosRestaurantes();
    
}
