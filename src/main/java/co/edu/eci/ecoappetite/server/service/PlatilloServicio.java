package co.edu.eci.ecoappetite.server.service;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface PlatilloServicio {
    
    PlatilloDTO agregarPlatillo(PlatilloDTO platilloDTO) throws EcoappetiteException;
    List<PlatilloDTO> consultarTodosLosPlatillos();
    PlatilloDTO consultarPlatilloPorId(String id) throws EcoappetiteException;
    List<PlatilloDTO> consultarPlatilloPorNombre(String nombre) throws EcoappetiteException;
    PlatilloDTO modificarPlatillo(String id, PlatilloDTO platilloDTO) throws EcoappetiteException;
}
