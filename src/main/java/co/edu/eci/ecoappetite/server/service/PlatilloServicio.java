package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface PlatilloServicio {

    PlatilloDTO agregarPlatillo(PlatilloDTO platilloDTO) throws EcoappetiteException;
    
}
