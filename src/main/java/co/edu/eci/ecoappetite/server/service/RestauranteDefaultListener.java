package co.edu.eci.ecoappetite.server.service;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;

@Service
public class RestauranteDefaultListener extends AbstractMongoEventListener<RestauranteEntidad>{

    @Override
    public void onBeforeConvert(BeforeConvertEvent<RestauranteEntidad> evento){
        RestauranteEntidad restauranteEntidad = evento.getSource();
        if(restauranteEntidad.getPlatillos() == null){
            restauranteEntidad.setPlatillos(List.of());
        }
    }
}
