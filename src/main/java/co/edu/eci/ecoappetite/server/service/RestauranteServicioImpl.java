package co.edu.eci.ecoappetite.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;

@Service
public class RestauranteServicioImpl implements RestauranteServicio {

    @Autowired
    private RestauranteRepositorio restauranteRepositorio;

    @Override
    public Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException {
        return restauranteRepositorio.registrarRestaurante(restaurante);
    }


    
}
