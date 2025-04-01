package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    public Carrito agregarProducto(Long carritoId, ItemCarrito item) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new
                RuntimeException("Carrito no encontrado"));
        carrito.getItems().add(item);
        return carritoRepository.save(carrito);
    }

    public Carrito eliminarProducto(Long carritoId, Long itemId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new
                RuntimeException("Carrito no encontrado"));
        carrito.getItems().removeIf(item -> item.getId().equals(itemId));
        return carritoRepository.save(carrito);
    }

    public void vaciarCarrito(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new
                RuntimeException("Carrito no encontrado"));
        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }

    public Carrito obtenerCarritoPorUsuario(Long usuarioId) {
        return carritoRepository.findByUsuarioId(usuarioId).orElseThrow(() -> new
                RuntimeException("Carrito no encontrado"));
    }

    public double calcularTotal(Long carritoId) {
        Carrito carrito = carritoRepository.findById(carritoId).orElseThrow(() -> new
                RuntimeException("Carrito no encontrado"));
        return carrito.getItems().stream().mapToDouble(ItemCarrito::getSubtotal).sum();
    }
}