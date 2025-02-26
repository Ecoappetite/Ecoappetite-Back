package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.ConsumidorMapper;
import co.edu.eci.ecoappetite.server.repository.ConsumidorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MongoConsumidorRepositorio implements ConsumidorRepositorio {

    private final MongoConsumidorInterface mongoConsumidorInterface;
    private final ConsumidorMapper consumidorMapper;

    @Autowired
    public MongoConsumidorRepositorio(MongoConsumidorInterface mongoConsumidorInterface, ConsumidorMapper consumidorMapper) {
        this.mongoConsumidorInterface = mongoConsumidorInterface;
        this.consumidorMapper = consumidorMapper;
    }

    @Override
    public Consumidor registrarConsumidor(Consumidor consumidor) throws EcoappetiteException {
        ConsumidorEntidad consumidorEntidad = consumidorMapper.toEntity(consumidor);
        ConsumidorEntidad consumidorGuardado = mongoConsumidorInterface.save(consumidorEntidad);
        return consumidorMapper.toDomain(consumidorGuardado);
    }

    @Override
    public void eliminarConsumidor(String id) throws EcoappetiteException {
        ConsumidorEntidad consumidor = mongoConsumidorInterface.findById(id)
                .orElseThrow(() -> new EcoappetiteException("El consumidor con ID " + id + " no existe."));

        mongoConsumidorInterface.delete(consumidor);
    }

    @Override
    public boolean existePorId(String id) {
        return mongoConsumidorInterface.findById(id).isPresent();
    }
}
