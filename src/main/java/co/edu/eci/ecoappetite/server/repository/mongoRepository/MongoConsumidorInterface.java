package co.edu.eci.ecoappetite.server.repository.mongoRepository;


import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import java.util.Optional;

public interface MongoConsumidorInterface extends MongoRepository<ConsumidorEntidad, String> {
    Optional<ConsumidorEntidad> findById(String id);
}
