package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.CarritoMapper;
import co.edu.eci.ecoappetite.server.repository.mongoRepository.MongoCarritoInterface;
import co.edu.eci.ecoappetite.server.domain.entity.CarritoEntidad;
import co.edu.eci.ecoappetite.server.repository.mongoRepository.MongoCarritoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CarritoService {

    @Autowired
    private final MongoCarritoInterface carritoRepository;
    private final MongoCarritoRepositorio carritoRepositorio;
    private final CarritoMapper carritoMapper;

    private final AtomicLong idCounter = new AtomicLong(1);

    public Carrito crearCarrito(Carrito nuevoCarrito) {
        nuevoCarrito.setItems(new ArrayList<>());
        nuevoCarrito.setProductos(new ArrayList<>());
        nuevoCarrito.setTotal(0.0);

        CarritoEntidad carritoEntidad = carritoMapper.toEntity(nuevoCarrito);
        carritoRepository.save(carritoEntidad);

        return nuevoCarrito;
    }




    // Método para agregar un producto al carrito
    public Carrito agregarProducto(String consumidorId, ItemCarrito item) throws NotFoundException{
        CarritoEntidad carritoEntidad = carritoRepository.findById(consumidorId)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));

        Carrito carrito = CarritoMapper.INSTANCE.toDomain(carritoEntidad);

        if (carrito.getProductos() == null) {
            carrito.setProductos(new ArrayList<>());
        }

        if (carrito.getItems() == null) {
            carrito.setItems(new ArrayList<>());
        }

        carrito.getProductos().add(item.getNombre()); // Agregar nombre del producto
        carrito.getItems().add(item); // Agregar el objeto completo

        carritoEntidad = CarritoMapper.INSTANCE.toEntity(carrito);
        carritoRepository.save(carritoEntidad);

        return carrito;
    }

    // Método para eliminar un producto del carrito
    public Carrito eliminarProducto(String carritoId, String itemId) throws NotFoundException {
        // Buscar el carrito en la base de datos
        CarritoEntidad carritoEntidad = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));

        // Convertir la entidad a dominio
        Carrito carrito = carritoMapper.toDomain(carritoEntidad);

        // Eliminar el producto de la lista de productos
        boolean productoEliminado = carrito.getProductos().removeIf(productoId -> Objects.equals(productoId, itemId));

        // Si el producto no fue encontrado, lanzar excepción
        if (!productoEliminado) {
            throw new NotFoundException("Producto no encontrado en el carrito");
        }

        // Guardar los cambios en la base de datos
        CarritoEntidad carritoActualizado = carritoRepository.save(carritoMapper.toEntity(carrito));

        // Retornar el carrito actualizado
        return carritoMapper.toDomain(carritoActualizado);
    }

    // Método para vaciar el carrito
    public void vaciarCarrito(String carritoId) throws NotFoundException {
        // Buscar el carrito en la base de datos
        CarritoEntidad carritoEntidad = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado"));

        // Convertir la entidad a dominio
        Carrito carrito = carritoMapper.toDomain(carritoEntidad);

        // Limpiar la lista de productos
        carrito.getProductos().clear();

        // Guardar los cambios en la base de datos
        carritoRepository.save(carritoMapper.toEntity(carrito));
    }

    // Método para obtener el carrito de un usuario
    public Carrito obtenerCarritoPorUsuario(String usuarioId) throws NotFoundException {
        // Buscar el carrito por usuarioId
        CarritoEntidad carritoEntidad = carritoRepository.findByConsumidorId(usuarioId)
                .orElseThrow(() -> new NotFoundException("Carrito no encontrado para el usuario"));

        // Retornar el carrito mapeado a dominio
        return carritoMapper.toDomain(carritoEntidad);
    }
}
