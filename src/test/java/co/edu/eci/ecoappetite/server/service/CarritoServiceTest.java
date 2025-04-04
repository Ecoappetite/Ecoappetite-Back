package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.entity.CarritoEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.CarritoMapper;
import co.edu.eci.ecoappetite.server.repository.mongoRepository.MongoCarritoInterface;
import co.edu.eci.ecoappetite.server.repository.mongoRepository.MongoCarritoRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private MongoCarritoInterface carritoRepository;

    @Mock
    private MongoCarritoRepositorio carritoRepositorio;

    @Mock
    private CarritoMapper carritoMapper;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carrito;
    private CarritoEntidad carritoEntidad;
    private ItemCarrito itemCarrito;

    @BeforeEach
    void setUp() {
        carrito = new Carrito("123", new ArrayList<>(), new ArrayList<>(), 0.0);
        carritoEntidad = new CarritoEntidad("123", new ArrayList<>(), 0.0);
        itemCarrito = new ItemCarrito("Pizza Margarita", 25.50);
    }

    @Test
    void testCrearCarrito() {
        when(carritoMapper.toEntity(any(Carrito.class))).thenReturn(carritoEntidad);
        when(carritoRepository.save(any(CarritoEntidad.class))).thenReturn(carritoEntidad);

        Carrito resultado = carritoService.crearCarrito(carrito);

        assertNotNull(resultado);
        assertEquals("123", resultado.getConsumidorId());
        assertTrue(resultado.getProductos().isEmpty());
        verify(carritoRepository, times(1)).save(any(CarritoEntidad.class));
    }

    @Test
    void testAgregarProducto() throws NotFoundException {
        when(carritoRepository.findById("123")).thenReturn(Optional.of(carritoEntidad));

        Carrito resultado = carritoService.agregarProducto("123", itemCarrito);

        assertNotNull(resultado);
        assertFalse(resultado.getProductos().isEmpty());
        assertEquals(1, resultado.getProductos().size());
        assertEquals("Pizza Margarita", resultado.getProductos().get(0));

        verify(carritoRepository, times(1)).save(any(CarritoEntidad.class));
    }


    @Test
    void testEliminarProducto() throws NotFoundException {
        carrito.getProductos().add("Pizza Margarita");
        carritoEntidad.getProductos().add("Pizza Margarita");

        when(carritoRepository.findById("123")).thenReturn(Optional.of(carritoEntidad));
        when(carritoMapper.toDomain(carritoEntidad)).thenReturn(carrito);
        when(carritoMapper.toEntity(any(Carrito.class))).thenReturn(carritoEntidad);
        when(carritoRepository.save(any(CarritoEntidad.class))).thenReturn(carritoEntidad);

        Carrito resultado = carritoService.eliminarProducto("123", "Pizza Margarita");

        assertNotNull(resultado);
        assertTrue(resultado.getProductos().isEmpty());
        verify(carritoRepository, times(1)).save(any(CarritoEntidad.class));
    }

    @Test
    void testEliminarProductoNoEncontrado() {
        when(carritoRepository.findById("123")).thenReturn(Optional.of(carritoEntidad));
        when(carritoMapper.toDomain(carritoEntidad)).thenReturn(carrito);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> carritoService.eliminarProducto("123", "Pizza Margarita"));

        assertEquals("Producto no encontrado en el carrito", exception.getMessage());
        verify(carritoRepository, never()).save(any(CarritoEntidad.class));
    }

    @Test
    void testVaciarCarrito() throws NotFoundException {
        carrito.getProductos().add("Pizza Margarita");
        carritoEntidad.getProductos().add("Pizza Margarita");

        when(carritoRepository.findById("123")).thenReturn(Optional.of(carritoEntidad));
        when(carritoMapper.toDomain(carritoEntidad)).thenReturn(carrito);
        when(carritoMapper.toEntity(any(Carrito.class))).thenReturn(carritoEntidad);

        carritoService.vaciarCarrito("123");

        assertTrue(carrito.getProductos().isEmpty());
        verify(carritoRepository, times(1)).save(any(CarritoEntidad.class));
    }

    @Test
    void testObtenerCarritoPorUsuario() throws NotFoundException {
        when(carritoRepository.findByConsumidorId("123")).thenReturn(Optional.of(carritoEntidad));
        when(carritoMapper.toDomain(carritoEntidad)).thenReturn(carrito);

        Carrito resultado = carritoService.obtenerCarritoPorUsuario("123");

        assertNotNull(resultado);
        assertEquals("123", resultado.getConsumidorId());
    }

    @Test
    void testObtenerCarritoPorUsuarioNoEncontrado() {
        when(carritoRepository.findByConsumidorId("123")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> carritoService.obtenerCarritoPorUsuario("123"));

        assertEquals("Carrito no encontrado para el usuario", exception.getMessage());
    }
}
