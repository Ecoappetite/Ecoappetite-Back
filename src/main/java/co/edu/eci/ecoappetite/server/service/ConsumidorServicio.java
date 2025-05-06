package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;

public interface ConsumidorServicio {
    ConsumidorDTO registrarConsumidor(ConsumidorDTO consumidorDTO) throws EcoappetiteException;

    ConsumidorDTO consultarConsumidorPorId(String id) throws NotFoundException;

    ConsumidorDTO modificarConsumidor(String id, ConsumidorDTO consumidorDTO) throws EcoappetiteException;

    void eliminarConsumidor(String id) throws EcoappetiteException;

}
