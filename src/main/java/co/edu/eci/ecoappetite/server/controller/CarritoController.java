package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @PostMapping("/{carritoId}/agregar")
    public ResponseEntity<Carrito> agregarProducto(@PathVariable Long carritoId,
                                                   @RequestBody ItemCarrito item) {
        return ResponseEntity.ok(carritoService.agregarProducto(carritoId, item));
    }

    @DeleteMapping("/{carritoId}/eliminar/{itemId}")
    public ResponseEntity<Carrito> eliminarProducto(@PathVariable Long carritoId,
                                                    @PathVariable Long itemId) {
        return ResponseEntity.ok(carritoService.eliminarProducto(carritoId, itemId));
    }

    @DeleteMapping("/{carritoId}/vaciar")
    public ResponseEntity<Void> vaciarCarrito(@PathVariable Long carritoId) {
        carritoService.vaciarCarrito(carritoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Carrito> obtenerCarritoPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarritoPorUsuario(usuarioId));
    }
}