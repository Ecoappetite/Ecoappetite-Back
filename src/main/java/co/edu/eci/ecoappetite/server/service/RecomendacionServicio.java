package co.edu.eci.ecoappetite.server.service;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RecomendacionServicio {

    List<RestauranteDTO> recomendarRestaurante(String id) throws EcoappetiteException;
    List<PlatilloDTO> recomendarPlatillo(String id) throws EcoappetiteException;
    
}
