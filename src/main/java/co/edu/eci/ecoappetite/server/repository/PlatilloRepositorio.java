package co.edu.eci.ecoappetite.server.repository;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface PlatilloRepositorio {

    Platillo agregarPlatillo(Platillo platillo) throws EcoappetiteException;
    List<Platillo> consultarTodosLosPlatillosos();
    Platillo consultarPlatilloPorId(String id) throws EcoappetiteException;
    List<Platillo> consultarPlatilloPorNombre(String nombre) throws EcoappetiteException;
    Platillo modificarPlatillo(String id, Platillo platillo) throws EcoappetiteException;
    void eliminarPlatillo(String id) throws EcoappetiteException;
    void eliminarPlatilloPorNitRestaurante(String nit) throws EcoappetiteException;
}
