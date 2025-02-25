package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.ConsumidorMapper;
import co.edu.eci.ecoappetite.server.repository.ConsumidorRepositorio;

@Repository
public class MongoConsumidorRepositorio {
    //Conecta la aplicación con MongoDB.
    private final MongoConsumidorInterface monGOconsumidorInterface;
    private final ConsumidorMapper consumidorMapper;

    @Autowired
    public MongoConsumidorRepositorio(MongoConsumidorInterface mongoConsumidorInterface, ConsumidorMapper consumidorMapper) {
        this.mongoConsumidorInterface = mongoConsumidorInterface;
        this.consumidorMapper = consumidorMapper;
    }

    @Override
    public Consumidor registrarConsumidor(Consumidor consumidor) throws EcoappetiteException {
        ConsumidorEntidad consumidorEntidad = consumidorMapper.toEntity(consumidor);
        ConsumidorEntidad consumidorRegistrado = mongoConsumidorInterface.save(consumidorEntidad);
        return consumidorMapper.toDomain(consumidorRegistrado);
    }
}
