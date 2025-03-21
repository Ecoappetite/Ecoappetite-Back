package com.ecoappetite.service;

import com.ecoappetite.model.Carrito;
import com.ecoappetite.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio del carrito de compras.
 */
@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    /**
     * Crea un nuevo carrito de compras asociado a un usuario y un restaurante.
     *
     * @param carrito Carrito a crear
     * @return Carrito guardado en la base de datos
     */
    public Carrito crearCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    /**
     * Busca un carrito por su ID.
     *
     * @param id Identificador del carrito
     * @return Carrito encontrado o vacío si no existe
     */
    public Optional<Carrito> obtenerCarritoPorId(Long id) {
        return carritoRepository.findById(id);
    }
}
