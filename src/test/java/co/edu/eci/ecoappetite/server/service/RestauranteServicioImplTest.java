package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.Categoria;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class RestauranteServicioImplTest {

    @InjectMocks
    private RestauranteServicioImpl restauranteServicio;

    @Mock
    private RestauranteRepositorio restauranteRepositorio;

    @Mock
    private RestauranteMapper restauranteMapper;

    private RestauranteDTO restauranteDTO;

    @BeforeEach
    public void setup() {
        restauranteDTO = new RestauranteDTO("123", "Restaurante Test", "Calle 123", "123456789", "987654321", null, "image_url", "Descripción del restaurante");
    }

    @Test
    public void testRegistrarRestaurante() throws EcoappetiteException {
        Restaurante restaurante = new Restaurante("123", "Restaurante Test", "Calle 123", "1234567890", "0987654321", Categoria.COLOMBIANA, "imagen.jpg", "Descripción de prueba");

        when(restauranteMapper.toDomain(restauranteDTO)).thenReturn(restaurante);
        when(restauranteRepositorio.registrarRestaurante(restaurante)).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.registrarRestaurante(restauranteDTO);

        assertNotNull(result);
        assertEquals("123", result.getNit());
        assertEquals("Restaurante Test", result.getNombre());
    }

    @Test
    public void testConsultarTodosLosRestaurantes() {
        Restaurante restaurante = new Restaurante("123", "Restaurante Test", "Calle 123", "1234567890", "0987654321", Categoria.COLOMBIANA, "imagen.jpg", "Descripción de prueba");

        when(restauranteRepositorio.consultarTodosLosRestaurantes()).thenReturn(Collections.singletonList(restaurante));
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        List<RestauranteDTO> restaurantes = restauranteServicio.consultarTodosLosRestaurantes();

        assertNotNull(restaurantes);
        assertFalse(restaurantes.isEmpty());
        assertEquals("Restaurante Test", restaurantes.get(0).getNombre());
    }

    @Test
    public void testConsultarRestaurantePorId() throws EcoappetiteException {
        Restaurante restaurante = new Restaurante("123", "Restaurante Test", "Calle 123", "1234567890", "0987654321", Categoria.COLOMBIANA, "imagen.jpg", "Descripción de prueba");

        when(restauranteRepositorio.consultarRestaurantePorId("123")).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.consultarRestaurantePorId("123");

        assertNotNull(result);
        assertEquals("123", result.getNit());
        assertEquals("Restaurante Test", result.getNombre());
    }



    @Test
    public void testConsultarRestaurantePorNombre() throws EcoappetiteException {
        Restaurante restaurante = new Restaurante("123", "Restaurante Test", "Calle 123", "1234567890", "0987654321", Categoria.COLOMBIANA, "imagen.jpg", "Descripción de prueba");

        when(restauranteRepositorio.consultarRestaurantePorNombre("Restaurante Test")).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.consultarRestaurantePorNombre("Restaurante Test");

        assertNotNull(result);
        assertEquals("Restaurante Test", result.getNombre());
    }

    @Test
    public void testModificarRestaurante() throws EcoappetiteException {
        Restaurante restaurante = new Restaurante("123", "Restaurante Test", "Calle 123", "1234567890", "0987654321", Categoria.COLOMBIANA, "imagen.jpg", "Descripción de prueba");

        when(restauranteMapper.toDomain(restauranteDTO)).thenReturn(restaurante);
        when(restauranteRepositorio.modificarRestaurante("123", restaurante)).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.modificarRestaurante("123", restauranteDTO);

        assertNotNull(result);
        assertEquals("123", result.getNit());
        assertEquals("Restaurante Test", result.getNombre());
    }

    @Test
    public void testEliminarRestaurante() throws EcoappetiteException {
        doNothing().when(restauranteRepositorio).eliminarRestaurante("123");

        restauranteServicio.eliminarRestaurante("123");

        assertDoesNotThrow(() -> restauranteServicio.eliminarRestaurante("123"));
    }

}
