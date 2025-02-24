package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Restaurante> consultarTodosLosRestaurantes() {
        return mongoRestauranteInterface.findAll()
                .stream()
                .map(restauranteMapper::toDomain)
                .collect(Collectors.toList());
      
    }

    @Override
    public Restaurante consultarRestaurantePorId(String id) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findById(id)
                .orElseThrow(() -> new EcoappetiteException("Este restaurante no fue encontrado"));
        
        return restauranteMapper.toDomain(restauranteEntidad);
    }

    @Override
    public Restaurante consultarRestaurantePorNombre(String nombre) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findByNombre(nombre)
                .orElseThrow(() -> new EcoappetiteException("Este restaurante no fue encontrado"));
        
        return restauranteMapper.toDomain(restauranteEntidad);
    }
    
}
