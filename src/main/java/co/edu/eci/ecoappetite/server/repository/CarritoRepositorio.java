package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;

public interface CarritoRepositorio {
    Carrito registrarCarrito(Carrito carrito) throws EcoappetiteException;
    Carrito consultarCarritoPorId(String id) throws NotFoundException;
    Carrito modificarCarrito(String id, Carrito carrito) throws EcoappetiteException;
    void eliminarCarrito(String id) throws EcoappetiteException;
}
