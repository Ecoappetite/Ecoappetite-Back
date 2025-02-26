package co.edu.eci.ecoappetite.server.repository.mongoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;


//Interfaz que permite acceder a MongoDB
public interface MongoConsumidorInterface extends MongoRepository<ConsumidorEntidad, String> {
}

