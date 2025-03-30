package co.edu.eci.ecoappetite.server.repository;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RestauranteRepositorio {
    Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException;
    List<Restaurante> consultarTodosLosRestaurantes();
    Restaurante consultarRestaurantePorId(String id) throws EcoappetiteException;
    Restaurante consultarRestaurantePorNombre(String nombre) throws EcoappetiteException;
    Restaurante modificarRestaurante(String id, Restaurante restaurante) throws EcoappetiteException;
    void eliminarRestaurante(String id) throws EcoappetiteException;
    Restaurante agregarPlatilloRestaurante(String nombre, Platillo platillo)throws EcoappetiteException;
    boolean existePlatillo(String nombre, Platillo platillo);
    void eliminarPlatilloRestaurante(String nit, String idPlatillo) throws EcoappetiteException;
    Restaurante modificarPlatilloRestaurante(String nit, String idPlatillo, Platillo platillo) throws EcoappetiteException;
}
