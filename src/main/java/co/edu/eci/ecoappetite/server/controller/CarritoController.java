package com.ecoappetite.controller;

import com.ecoappetite.model.Carrito;
import com.ecoappetite.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar las operaciones del carrito de compras.
 */
@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    /**
     * Endpoint para crear un nuevo carrito.
     *
     * @param carrito Objeto carrito recibido en el request
     * @return Carrito creado con su ID asignado
     */
    @PostMapping
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.crearCarrito(carrito);
        return ResponseEntity.ok(nuevoCarrito);
    }

    /**
     * Endpoint para obtener un carrito por su ID.
     *
     * @param id ID del carrito
     * @return Carrito encontrado o error 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> obtenerCarrito(@PathVariable Long id) {
        return carritoService.obtenerCarritoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
