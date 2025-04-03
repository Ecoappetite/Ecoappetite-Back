package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.eci.ecoappetite.server.domain.entity.RegistroHistorialEntidad;

public interface MongoRegistroHistorialInterface extends MongoRepository<RegistroHistorialEntidad, String>{
    
    List<RegistroHistorialEntidad> findByIdConsumidor(String idConsumidor);
    
}
