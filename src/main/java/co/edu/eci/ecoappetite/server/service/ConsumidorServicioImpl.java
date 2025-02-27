package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.entity.ConsumidorEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
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
        // Convertimos DTO a Entidad
        ConsumidorEntidad consumidorEntidad = consumidorMapper.toEntity(consumidorMapper.toDomain(consumidorDTO));
        // Guardamos en la base de datos
        ConsumidorEntidad nuevoConsumidor = consumidorRepositorio.registrarConsumidor(consumidorEntidad);
        // Convertimos a DTO antes de retornar
        return consumidorMapper.toDTO(consumidorMapper.toDomain(nuevoConsumidor));
    }

    @Override
    public void eliminarConsumidor(String id) throws EcoappetiteException {
        if (!consumidorRepositorio.existePorId(id)) {
            throw new EcoappetiteException("El consumidor con ID " + id + " no existe.");
        }
        consumidorRepositorio.eliminarConsumidor(id);
    }

    @Override
    public ConsumidorDTO consultarConsumidorPorId(String id) throws EcoappetiteException {
        Consumidor consumidor = consumidorRepositorio.consulatrConsumidorPorId(id);
        return consumidorMapper.toDTO(consumidor);
    }

    @Override
    public ConsumidorDTO modificarConsumidor(String id, ConsumidorDTO consumidorDTO) throws EcoappetiteException {
        Consumidor consumidor = consumidorMapper.toDomain(consumidorDTO);
        Consumidor consumidorModificado= consumidorRepositorio.modificarConsumidor(id, consumidor);
        return consumidorMapper.toDTO(consumidorModificado);

    }
}
