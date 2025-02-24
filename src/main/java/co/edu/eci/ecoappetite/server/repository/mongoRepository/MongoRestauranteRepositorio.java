package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;

@Repository
public class MongoRestauranteRepositorio implements RestauranteRepositorio{

    private MongoRestauranteInterface mongoRestauranteInterface;
    private RestauranteMapper restauranteMapper;

    @Autowired
    public MongoRestauranteRepositorio(MongoRestauranteInterface mongoRestauranteInterface, RestauranteMapper restauranteMapper){
        this.mongoRestauranteInterface = mongoRestauranteInterface;
        this.restauranteMapper = restauranteMapper;
    }

    @Override
    public Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = restauranteMapper.toEntity(restaurante);
        RestauranteEntidad restauranteRegistrado = mongoRestauranteInterface.save(restauranteEntidad);
        return restauranteMapper.toDomain(restauranteRegistrado);
    }
    
}
