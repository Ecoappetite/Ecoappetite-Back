package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface ConsumidorServicio {
    ConsumidorDTO registrarConsumidor(ConsumidorDTO consumidorDTO) throws EcoappetiteException;
    ConsumidorDTO consultarConsumidorPorId(String id) throws EcoappetiteException;

    ConsumidorDTO modificarConsumidor(String id, ConsumidorDTO consumidorDTO) throws EcoappetiteException;
}
