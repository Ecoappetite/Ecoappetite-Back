package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import co.edu.eci.ecoappetite.server.domain.entity.CarritoEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.CarritoMapper;
import co.edu.eci.ecoappetite.server.repository.CarritoRepositorio;

@Repository
public class MongoCarritoRepositorio implements CarritoRepositorio {

    private final MongoCarritoInterface mongoCarritoInterface;
    private final CarritoMapper carritoMapper;

    @Autowired
    public MongoCarritoRepositorio(MongoCarritoInterface mongoCarritoInterface, CarritoMapper carritoMapper) {
        this.mongoCarritoInterface = mongoCarritoInterface;
        this.carritoMapper = carritoMapper;
    }

    @Override
    public Carrito registrarCarrito(Carrito carrito) throws EcoappetiteException {
        CarritoEntidad carritoEntidad = carritoMapper.toEntity(carrito);
        CarritoEntidad carritoRegistrado = mongoCarritoInterface.save(carritoEntidad);
        return carritoMapper.toDomain(carritoRegistrado);
    }

    @Override
    public Carrito consultarCarritoPorId(String id) throws NotFoundException {
        CarritoEntidad carritoEntidad = mongoCarritoInterface.findById(id)
                .orElseThrow(() -> new NotFoundException("El carrito no ha sido encontrado"));

        return carritoMapper.toDomain(carritoEntidad);
    }

    @Override
    public Carrito modificarCarrito(String id, Carrito carrito) throws EcoappetiteException {
        CarritoEntidad carritoEntidad = mongoCarritoInterface.findById(id)
                .orElseThrow(() -> new EcoappetiteException("El carrito no ha sido encontrado"));

        carritoEntidad.setProductos(carrito.getProductos());
        carritoEntidad.setTotal(carrito.getTotal());

        CarritoEntidad carritoModificado = mongoCarritoInterface.save(carritoEntidad);
        return carritoMapper.toDomain(carritoModificado);
    }

    @Override
    public void eliminarCarrito(String id) throws EcoappetiteException {
        mongoCarritoInterface.deleteById(id);
    }
}
