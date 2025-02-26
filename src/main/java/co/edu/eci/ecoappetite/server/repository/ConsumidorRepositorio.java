package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumidorRepositorio extends MongoRepository<ConsumidorEntidad, String> {

    // Verifica si un consumidor existe por su ID
    default boolean existePorId(String id) {
        return existsById(id);
    }

    // Guarda un nuevo consumidor en la base de datos
    default ConsumidorEntidad registrarConsumidor(ConsumidorEntidad consumidor) {
        return save(consumidor);
    }

    // Elimina un consumidor por ID
    default void eliminarConsumidor(String id) {
        deleteById(id);
    }
}