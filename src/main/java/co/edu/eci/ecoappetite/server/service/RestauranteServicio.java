package co.edu.eci.ecoappetite.server.service;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RestauranteServicio {

    RestauranteDTO registrarRestaurante(RestauranteDTO restauranteDTO) throws EcoappetiteException;
    List<RestauranteDTO> consultarTodosLosRestaurantes();
    RestauranteDTO consultarRestaurantePorId(String id) throws EcoappetiteException;
    RestauranteDTO consultarRestaurantePorNombre(String nombre) throws EcoappetiteException;
    RestauranteDTO modificarRestaurante(String id, RestauranteDTO restauranteDTO) throws EcoappetiteException;
    void eliminarRestaurante(String id) throws EcoappetiteException;
    void agregarPlatilloRestaurante(String nombre, PlatilloDTO platilloDTO)throws EcoappetiteException;
    void eliminarPlatilloRestaurante(String nit, String idPlatillo)throws EcoappetiteException;
    void modificarPlatilloRestaurante(String nit, String idPlatillo, PlatilloDTO platilloDTO) throws EcoappetiteException;
    RestauranteDTO modificarCantidadPlatilloRestaurante(String nit, String idPlatillo, Integer cantidad) throws EcoappetiteException;
    
}   
