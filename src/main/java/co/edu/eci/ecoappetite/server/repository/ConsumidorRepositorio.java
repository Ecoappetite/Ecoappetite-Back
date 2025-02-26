package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface ConsumidorRepositorio {
    Consumidor registrarConsumidor(Consumidor consumidor) throws EcoappetiteException;
    void eliminarConsumidor(String id) throws EcoappetiteException;
    boolean existePorId(String id);
}
