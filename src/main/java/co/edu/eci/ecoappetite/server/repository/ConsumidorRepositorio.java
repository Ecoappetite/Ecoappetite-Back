package co.edu.eci.ecoappetite.server.repository;


import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;


public interface ConsumidorRepositorio {
    Consumidor registrarConsumidor(Consumidor consumidor) throws EcoappetiteException;
    Consumidor consultarConsumidorPorId(String id) throws NotFoundException;
    Consumidor modificarConsumidor(String id, Consumidor consumidor) throws EcoappetiteException;
     void eliminarConsumidor(String id) throws EcoappetiteException;
}

