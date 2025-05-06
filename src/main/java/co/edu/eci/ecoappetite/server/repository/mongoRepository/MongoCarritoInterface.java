package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import co.edu.eci.ecoappetite.server.domain.entity.CarritoEntidad;

@Repository
public interface MongoCarritoInterface extends MongoRepository<CarritoEntidad, String> {
    Optional<CarritoEntidad> findByConsumidorId(String consumidorId);

}
