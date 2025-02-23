package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RestauranteServicio {

    Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException;
    
}
