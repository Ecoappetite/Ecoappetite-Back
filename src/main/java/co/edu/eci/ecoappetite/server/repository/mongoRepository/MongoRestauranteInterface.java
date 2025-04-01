package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;

public interface MongoRestauranteInterface extends MongoRepository<RestauranteEntidad, String>{

    Optional<RestauranteEntidad> findByNombre(String nombre);
    boolean existsByNombreAndPlatillosNombre(String restaurante, String platillo);
    
}
