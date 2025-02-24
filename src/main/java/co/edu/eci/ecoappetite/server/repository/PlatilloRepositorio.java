package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface PlatilloRepositorio {

    Platillo agregarPlatillo(Platillo platillo) throws EcoappetiteException;
    
}
