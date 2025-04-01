package co.edu.eci.ecoappetite.server.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.Categoria;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.DuplicationErrorException;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.service.RestauranteServicio;

class RestauranteControllerTest {

        @Mock
    private RestauranteServicio restauranteServicio;

    @InjectMocks
    private RestauranteController restauranteController;

    private RestauranteDTO restauranteDTO;
    private PlatilloDTO platilloDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restauranteDTO = new RestauranteDTO();
        restauranteDTO.setNit("123");
        restauranteDTO.setNombre("Pizza Mundo");
        restauranteDTO.setDireccion("Calle 123");
        restauranteDTO.setTelefono("3101234567");
        restauranteDTO.setWhatsapp("3101234567");
        restauranteDTO.setImagen("imagen.png");
        restauranteDTO.setDescripcion("Deliciosa pizza");
        restauranteDTO.setCategoria(Categoria.ITALIANA);
        restauranteDTO.setPlatillos(List.of());

        platilloDTO = new PlatilloDTO();
        platilloDTO.setId("platillo1");
        platilloDTO.setNombre("Pizza Margarita");
    }

    @Test
    void registrarRestaurante_deberiaRetornarMensajeDeExito() throws EcoappetiteException {
        when(restauranteServicio.registrarRestaurante(any())).thenReturn(restauranteDTO);

        ResponseEntity<String> response = restauranteController.registrarRestaurante(restauranteDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Restaurante :" + restauranteDTO.getNombre() + " registrado", response.getBody());
    }


    @Test
    void consultarTodosLosRestaurantes_deberiaRetornarLista() {
        when(restauranteServicio.consultarTodosLosRestaurantes()).thenReturn(List.of(restauranteDTO));

        ResponseEntity<List<RestauranteDTO>> response = restauranteController.consultarTodosLosRestaurantes();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void consultarRestaurantePorId_deberiaRetornarRestaurante() throws EcoappetiteException {
        when(restauranteServicio.consultarRestaurantePorId("123")).thenReturn(restauranteDTO);

        ResponseEntity<RestauranteDTO> response = restauranteController.consultarRestaurantePorId("123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pizza Mundo", response.getBody().getNombre());
    }

    @Test
    void consultarRestaurantePorNombre_deberiaRetornarRestaurante() throws EcoappetiteException {
        when(restauranteServicio.consultarRestaurantePorNombre("Pizza Mundo")).thenReturn(restauranteDTO);

        ResponseEntity<RestauranteDTO> response = restauranteController.consultarRestaurantePorNombre("Pizza Mundo");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pizza Mundo", response.getBody().getNombre());
    }

    @Test
    void modificarRestaurante_deberiaRetornarMensajeDeModificacion() throws EcoappetiteException {
        when(restauranteServicio.modificarRestaurante(eq("123"), any())).thenReturn(restauranteDTO);

        ResponseEntity<String> response = restauranteController.modificarRestaurante("123", restauranteDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("El restaurante: " + restauranteDTO.getNombre() + " ha sido modificado.", response.getBody());
    }

    @Test
    void eliminarRestaurante_deberiaRetornarMensajeExito() throws EcoappetiteException {
        doNothing().when(restauranteServicio).eliminarRestaurante("123");

        ResponseEntity<String> response = restauranteController.eliminarRestaurante("123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("El restaurante: 123 ha sido eliminado.", response.getBody());
    }

    @Test
    void modificarCantidadPlatilloRestaurante_deberiaRetornarMensajeExito() throws EcoappetiteException {
        when(restauranteServicio.modificarCantidadPlatilloRestaurante("123", "platillo1", 2)).thenReturn(restauranteDTO);

        ResponseEntity<String> response = restauranteController.modificarCantidadPlatilloRestaurante("123", "platillo1", 2);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("La cantidad del platillo: platillo1 ha sido modificada", response.getBody());
    }

    @Test
    void agregarPlatilloRestaurante_deberiaRetornarMensajeExito() throws EcoappetiteException {
        doNothing().when(restauranteServicio).agregarPlatilloRestaurante("Pizza Mundo", platilloDTO);

        ResponseEntity<String> response = restauranteController.agregarPlatilloRestaurante("Pizza Mundo", platilloDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("El platillo: " + platilloDTO.getNombre() + " ha sido agregado al restaurante.", response.getBody());
    }

    @Test
    void eliminarPlatilloRestaurante_deberiaRetornarMensajeExito() throws EcoappetiteException {
        doNothing().when(restauranteServicio).eliminarPlatilloRestaurante("123", "platillo1");

        ResponseEntity<String> response = restauranteController.eliminarPlatilloRestaurante("123", "platillo1");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("El platillo: platillo1 ha sido eliminado", response.getBody());
    }

    @Test
    void modificarPlatilloRestaurante_deberiaRetornarMensajeExito() throws EcoappetiteException {
        doNothing().when(restauranteServicio).modificarPlatilloRestaurante("123", "platillo1", platilloDTO);

        ResponseEntity<String> response = restauranteController.modificarPlatilloRestaurante("123", "platillo1", platilloDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("El Platillo: " + platilloDTO.getNombre() + " ha sido modificado", response.getBody());
    }

    @Test
    void registrarRestaurante_conDatosInvalidos_deberiaLanzarDataValidationException() throws EcoappetiteException {
        when(restauranteServicio.registrarRestaurante(any()))
            .thenThrow(new DataValidationException("Datos inválidos"));
    
        assertThrows(DataValidationException.class, () -> {
            restauranteController.registrarRestaurante(restauranteDTO);
        });
    }
    

    @Test
    void consultarRestaurantePorNombre_inexistente_deberiaLanzarNotFoundException() throws EcoappetiteException {
        when(restauranteServicio.consultarRestaurantePorNombre("NoExiste"))
            .thenThrow(new NotFoundException("Restaurante no encontrado"));
    
        assertThrows(NotFoundException.class, () -> {
            restauranteController.consultarRestaurantePorNombre("NoExiste");
        });
    }
    

    @Test
    void consultarTodosLosRestaurantes_sinDatos_deberiaRetornarListaVacia() {
        when(restauranteServicio.consultarTodosLosRestaurantes()).thenReturn(List.of());

        var response = restauranteController.consultarTodosLosRestaurantes();

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void agregarPlatilloRestaurante_duplicado_deberiaRetornarBadRequest() throws EcoappetiteException {
        doNothing().when(restauranteServicio).agregarPlatilloRestaurante(any(), any());

        doNothing().when(restauranteServicio).agregarPlatilloRestaurante("Pizza Mundo", platilloDTO);

        ResponseEntity<String> response = restauranteController.agregarPlatilloRestaurante("Pizza Mundo", platilloDTO);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void modificarCantidadPlatilloRestaurante_cantidadMayorQueDisponible_deberiaLanzarDataValidationException() throws EcoappetiteException {
        when(restauranteServicio.modificarCantidadPlatilloRestaurante("123", "platillo1", 1000))
            .thenThrow(new DataValidationException("La cantidad a comprar es mayor que la disponible"));
    
        assertThrows(DataValidationException.class, () -> {
            restauranteController.modificarCantidadPlatilloRestaurante("123", "platillo1", 1000);
        });
    }    

    @Test
    void registrarRestaurante_conNullPointerException_deberiaLanzarNullPointerException() throws EcoappetiteException {
        when(restauranteServicio.registrarRestaurante(any()))
            .thenThrow(new NullPointerException("Faltan datos"));
    
        assertThrows(NullPointerException.class, () -> {
            restauranteController.registrarRestaurante(restauranteDTO);
        });
    }

    @Test
    void modificarPlatilloRestaurante_inexistente_deberiaLanzarNotFoundException() throws EcoappetiteException {
        doThrow(new NotFoundException("El platillo no fue encontrado")).when(restauranteServicio)
            .modificarPlatilloRestaurante("123", "inexistente", platilloDTO);

        assertThrows(NotFoundException.class, () -> {
            restauranteController.modificarPlatilloRestaurante("123", "inexistente", platilloDTO);
        });
    }

    @Test
    void agregarPlatilloRestaurante_duplicado_deberiaLanzarDuplicationErrorException() throws EcoappetiteException {
        doThrow(new DuplicationErrorException("El platillo ya existe")).when(restauranteServicio)
            .agregarPlatilloRestaurante("Pizza Mundo", platilloDTO);

        assertThrows(DuplicationErrorException.class, () -> {
            restauranteController.agregarPlatilloRestaurante("Pizza Mundo", platilloDTO);
        });
    }

    @Test
    void eliminarPlatilloRestaurante_inexistente_deberiaLanzarNotFoundException() throws EcoappetiteException {
        doThrow(new NotFoundException("Platillo no encontrado")).when(restauranteServicio)
            .eliminarPlatilloRestaurante("123", "platilloInexistente");

        assertThrows(NotFoundException.class, () -> {
            restauranteController.eliminarPlatilloRestaurante("123", "platilloInexistente");
        });
    }
    
    @Test
    void modificarCantidadPlatilloRestaurante_platilloNoExiste_deberiaLanzarNotFoundException() throws EcoappetiteException {
        when(restauranteServicio.modificarCantidadPlatilloRestaurante("123", "inexistente", 1))
            .thenThrow(new NotFoundException("Platillo no encontrado"));

        assertThrows(NotFoundException.class, () -> {
            restauranteController.modificarCantidadPlatilloRestaurante("123", "inexistente", 1);
        });
    }

    @Test
    void registrarRestaurante_conPlatillosNull_deberiaRegistrarExitosamente() throws EcoappetiteException {
        restauranteDTO.setPlatillos(null);
        when(restauranteServicio.registrarRestaurante(any())).thenReturn(restauranteDTO);

        ResponseEntity<String> response = restauranteController.registrarRestaurante(restauranteDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Restaurante :" + restauranteDTO.getNombre() + " registrado", response.getBody());
    }

}
