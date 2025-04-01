package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.PlatilloServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatilloControllerTest {

    @Mock
    private PlatilloServicio platilloServicio;

    @InjectMocks
    private PlatilloController platilloController;

    private PlatilloDTO platilloDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        platilloDTO = new PlatilloDTO();
        platilloDTO.setId("1");
        platilloDTO.setNombre("Hamburguesa");
        platilloDTO.setPrecioOriginal(20000.0);
        platilloDTO.setPrecioDescuento(15000.0);
        platilloDTO.setCantidadDisponible(10);
        platilloDTO.setNitRestaurante("123456789");
    }

    @Test
    void consultarTodosLosPlatillos_deberiaRetornarListaDePlatillos() {
        List<PlatilloDTO> lista = Arrays.asList(platilloDTO);
        when(platilloServicio.consultarTodosLosPlatillos()).thenReturn(lista);

        ResponseEntity<List<PlatilloDTO>> response = platilloController.consultarTodosLosPlatillos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Hamburguesa", response.getBody().get(0).getNombre());
    }

    @Test
    void consultarPlatilloPorId_deberiaRetornarPlatillo() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorId("1")).thenReturn(platilloDTO);

        ResponseEntity<PlatilloDTO> response = platilloController.consultarPlatilloPorId("1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Hamburguesa", response.getBody().getNombre());
    }

    @Test
    void consultarPlatilloPorNombre_deberiaRetornarListaDePlatillos() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorNombre("Hamburguesa"))
                .thenReturn(Arrays.asList(platilloDTO));

        ResponseEntity<List<PlatilloDTO>> response = platilloController.consultarPlatilloPorNombre("Hamburguesa");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Hamburguesa", response.getBody().get(0).getNombre());
    }

    @Test
    void consultarPlatilloPorId_cuandoNoExiste_deberiaLanzarExcepcion() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorId("999"))
            .thenThrow(new EcoappetiteException("Platillo no encontrado"));

        EcoappetiteException exception = assertThrows(
            EcoappetiteException.class,
            () -> platilloController.consultarPlatilloPorId("999")
        );

        assertEquals("Platillo no encontrado", exception.getMessage());
    }

    @Test
    void consultarPlatilloPorNombre_cuandoNoExiste_deberiaLanzarExcepcion() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorNombre("Inexistente"))
            .thenThrow(new EcoappetiteException("No se encontraron platillos con ese nombre"));

        EcoappetiteException exception = assertThrows(
            EcoappetiteException.class,
            () -> platilloController.consultarPlatilloPorNombre("Inexistente")
        );

        assertEquals("No se encontraron platillos con ese nombre", exception.getMessage());
    }

    @Test
    void consultarTodosLosPlatillos_deberiaRetornarListaVacia() {
        when(platilloServicio.consultarTodosLosPlatillos()).thenReturn(Arrays.asList());

        ResponseEntity<List<PlatilloDTO>> response = platilloController.consultarTodosLosPlatillos();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void consultarPlatilloPorId_deberiaLanzarExcepcion() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorId("1")).thenThrow(new RuntimeException("Error inesperado"));

        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> platilloController.consultarPlatilloPorId("1")
        );

        assertEquals("Error inesperado", exception.getMessage());
    }

    @Test
    void consultarPlatilloPorNombre_nombreVacio_deberiaLanzarExcepcion() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorNombre("")).thenThrow(new EcoappetiteException("Nombre no puede estar vacío"));

        EcoappetiteException exception = assertThrows(
            EcoappetiteException.class,
            () -> platilloController.consultarPlatilloPorNombre("")
        );

        assertEquals("Nombre no puede estar vacío", exception.getMessage());
    }

    @Test
    void consultarPlatilloPorNombre_multiplesPlatillos_deberiaRetornarLista() throws EcoappetiteException {
        PlatilloDTO otroPlatillo = new PlatilloDTO();
        otroPlatillo.setId("2");
        otroPlatillo.setNombre("Hamburguesa");

        when(platilloServicio.consultarPlatilloPorNombre("Hamburguesa"))
            .thenReturn(Arrays.asList(platilloDTO, otroPlatillo));

        var response = platilloController.consultarPlatilloPorNombre("Hamburguesa");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

}
