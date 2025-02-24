package co.edu.eci.ecoappetite.server.repository;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RestauranteRepositorio {
    Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException;
    List<Restaurante> consultarTodosLosRestaurantes();
    
}
