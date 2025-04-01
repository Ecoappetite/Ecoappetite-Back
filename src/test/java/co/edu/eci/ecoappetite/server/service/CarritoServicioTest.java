package co.edu.eci.ecoappetite.server.service;


import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.repository.CarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CarritoServicioTest {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carrito;
    private ItemCarrito item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setItems(new ArrayList<>());

        item = new ItemCarrito();
        item.setId(1L);
        item.setCantidad(2);
        carrito.getItems().add(item);
    }

    @Test
    void testAgregarProducto() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.agregarProducto(1L, item);

        assertEquals(2, result.getItems().size());
        verify(carritoRepository, times(1)).findById(1L);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    void testEliminarProducto() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.eliminarProducto(1L, 1L);

        assertEquals(0, result.getItems().size());
        verify(carritoRepository, times(1)).findById(1L);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    void testVaciarCarrito() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));

        carritoService.vaciarCarrito(1L);

        assertEquals(0, carrito.getItems().size());
        verify(carritoRepository, times(1)).findById(1L);
        verify(carritoRepository, times(1)).save(carrito);
    }

    @Test
    void testObtenerCarritoPorUsuario() {
        when(carritoRepository.findByUsuarioId(1L)).thenReturn(Optional.of(carrito));

        Carrito result = carritoService.obtenerCarritoPorUsuario(1L);

        assertEquals(carrito, result);
        verify(carritoRepository, times(1)).findByUsuarioId(1L);
    }

}
