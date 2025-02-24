package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RestauranteRepositorio {
    Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException;
    
}
