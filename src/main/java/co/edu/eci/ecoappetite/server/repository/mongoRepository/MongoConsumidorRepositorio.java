package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.ConsumidorMapper;
import co.edu.eci.ecoappetite.server.repository.ConsumidorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoConsumidorRepositorio {

    private final ConsumidorRepositorio consumidorRepositorio;
    private final ConsumidorMapper consumidorMapper;

    @Autowired
    public MongoConsumidorRepositorio(ConsumidorRepositorio consumidorRepositorio, ConsumidorMapper consumidorMapper) {
        this.consumidorRepositorio = consumidorRepositorio;
        this.consumidorMapper = consumidorMapper;
    }

    public Consumidor registrarConsumidor(Consumidor consumidor) {
        ConsumidorEntidad consumidorEntidad = consumidorMapper.toEntity(consumidor);
        ConsumidorEntidad consumidorGuardado = consumidorRepositorio.save(consumidorEntidad);
        return consumidorMapper.toDomain(consumidorGuardado);
    }

    public void eliminarConsumidor(String id) throws EcoappetiteException {
        if (!consumidorRepositorio.existsById(id)) {
            throw new EcoappetiteException("El consumidor con ID " + id + " no existe.");
        }
        consumidorRepositorio.deleteById(id);
    }

    public boolean existePorId(String id) {
        return consumidorRepositorio.existsById(id);
    }
}
