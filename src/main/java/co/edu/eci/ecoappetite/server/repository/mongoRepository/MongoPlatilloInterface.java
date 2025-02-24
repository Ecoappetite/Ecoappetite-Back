package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.eci.ecoappetite.server.domain.entity.PlatilloEntidad;

public interface MongoPlatilloInterface extends MongoRepository<PlatilloEntidad, String>{
    
}
