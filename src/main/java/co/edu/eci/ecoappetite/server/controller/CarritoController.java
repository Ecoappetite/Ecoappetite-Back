package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Carrito> crearCarrito(@RequestBody Carrito nuevoCarrito) {
        if (nuevoCarrito.getConsumidorId() == null || nuevoCarrito.getConsumidorId().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Carrito carritoGuardado = carritoService.crearCarrito(nuevoCarrito);
        return ResponseEntity.ok(carritoGuardado);
    }



    // Método para agregar un producto al carrito
    @PostMapping("/{carritoId}/agregar")
    public ResponseEntity<Carrito> agregarProducto(@PathVariable("carritoId") String carritoId,
                                                   @RequestBody ItemCarrito item) throws NotFoundException { // Declaramos la excepción aquí
        Carrito carritoActualizado = carritoService.agregarProducto(carritoId, item);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Método para eliminar un producto del carrito
    @DeleteMapping("/{carritoId}/eliminar/{itemId}")
    public ResponseEntity<Carrito> eliminarProducto(@PathVariable("carritoId") String carritoId,
                                                    @PathVariable("itemId") String itemId) throws NotFoundException { // Declaramos la excepción aquí
        Carrito carritoActualizado = carritoService.eliminarProducto(carritoId, itemId);
        return ResponseEntity.ok(carritoActualizado);
    }

    // Método para vaciar un carrito
    @DeleteMapping("/{carritoId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable("carritoId") String carritoId) throws NotFoundException { // Declaramos la excepción aquí
        carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.noContent().build();
    }

    // Método para obtener el carrito de un usuario específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarritoPorUsuario(@PathVariable("usuarioId") String usuarioId) throws NotFoundException { // Declaramos la excepción aquí
        Carrito carrito = carritoService.obtenerCarritoPorUsuario(usuarioId);
        return ResponseEntity.ok(carrito);
    }
}
