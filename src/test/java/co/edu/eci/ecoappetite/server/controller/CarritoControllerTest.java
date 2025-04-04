package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.model.Carrito;
import co.edu.eci.ecoappetite.server.domain.model.ItemCarrito;
import co.edu.eci.ecoappetite.server.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarritoControllerTest {

    @Mock
    private CarritoService carritoService;

    @InjectMocks
    private CarritoController carritoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearCarrito() {
        Carrito carrito = new Carrito("123", Collections.emptyList(), Collections.emptyList(), 0.0);
        when(carritoService.crearCarrito(any(Carrito.class))).thenReturn(carrito);

        ResponseEntity<Carrito> response = carritoController.crearCarrito(carrito);

        assertNotNull(response.getBody());
        assertEquals("123", response.getBody().getConsumidorId());
        verify(carritoService, times(1)).crearCarrito(any(Carrito.class));
    }

    @Test
    void testAgregarProducto() throws Exception {
        ItemCarrito item = new ItemCarrito("Pizza Margarita", 25.50);
        Carrito carrito = new Carrito("123", Collections.singletonList(item), Collections.emptyList(), 25.50);
        when(carritoService.agregarProducto("123", item)).thenReturn(carrito);

        ResponseEntity<Carrito> response = carritoController.agregarProducto("123", item);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getItems().size());
        verify(carritoService, times(1)).agregarProducto("123", item);
    }

    @Test
    void testEliminarProducto() throws Exception {
        Carrito carrito = new Carrito("123", Collections.emptyList(), Collections.emptyList(), 0.0);
        when(carritoService.eliminarProducto("123", "1")).thenReturn(carrito);

        ResponseEntity<Carrito> response = carritoController.eliminarProducto("123", "1");

        assertNotNull(response.getBody());
        verify(carritoService, times(1)).eliminarProducto("123", "1");
    }

    @Test
    void testVaciarCarrito() throws Exception {
        doNothing().when(carritoService).vaciarCarrito("123");

        ResponseEntity<Void> response = carritoController.vaciarCarrito("123");

        assertEquals(204, response.getStatusCodeValue());
        verify(carritoService, times(1)).vaciarCarrito("123");
    }

    @Test
    void testObtenerCarritoPorUsuario() throws Exception {
        Carrito carrito = new Carrito("123", Collections.emptyList(), Collections.emptyList(), 0.0);
        when(carritoService.obtenerCarritoPorUsuario("123")).thenReturn(carrito);

        ResponseEntity<Carrito> response = carritoController.obtenerCarritoPorUsuario("123");

        assertNotNull(response.getBody());
        assertEquals("123", response.getBody().getConsumidorId());
        verify(carritoService, times(1)).obtenerCarritoPorUsuario("123");
    }
}
