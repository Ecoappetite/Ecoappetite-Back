package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.ConsumidorMapper;
import co.edu.eci.ecoappetite.server.repository.ConsumidorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumidorServicioImpl implements ConsumidorServicio {

    private final ConsumidorRepositorio consumidorRepositorio;
    private final ConsumidorMapper consumidorMapper;

    @Autowired
    public ConsumidorServicioImpl(ConsumidorRepositorio consumidorRepositorio, ConsumidorMapper consumidorMapper) {
        this.consumidorRepositorio = consumidorRepositorio;
        this.consumidorMapper = consumidorMapper;
    }

    @Override
    public ConsumidorDTO registrarConsumidor(ConsumidorDTO consumidorDTO) throws EcoappetiteException {
        Consumidor consumidorEntidad = consumidorMapper.toDomain(consumidorDTO);
        Consumidor nuevoConsumidor = consumidorRepositorio.registrarConsumidor(consumidorEntidad);
        return consumidorMapper.toDTO(nuevoConsumidor);
    }

    @Override
    public void eliminarConsumidor(String id) throws EcoappetiteException {
        consumidorRepositorio.eliminarConsumidor(id);
    }

    @Override
    public ConsumidorDTO consultarConsumidorPorId(String id) throws NotFoundException {
        Consumidor consumidor = consumidorRepositorio.consultarConsumidorPorId(id);
        return consumidorMapper.toDTO(consumidor);
    }

    @Override
    public ConsumidorDTO modificarConsumidor(String id, ConsumidorDTO consumidorDTO) throws EcoappetiteException {
        Consumidor consumidor = consumidorMapper.toDomain(consumidorDTO);
        Consumidor consumidorModificado= consumidorRepositorio.modificarConsumidor(id, consumidor);
        return consumidorMapper.toDTO(consumidorModificado);

    }
}
