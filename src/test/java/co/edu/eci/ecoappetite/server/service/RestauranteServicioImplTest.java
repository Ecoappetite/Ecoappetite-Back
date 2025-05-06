package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.Categoria;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestauranteServicioImplTest {

    @Mock
    private RestauranteRepositorio restauranteRepositorio;

    @Mock
    private RestauranteMapper restauranteMapper;

    @Mock
    private PlatilloMapper platilloMapper;

    @Mock
    private PlatilloServicio platilloServicio;

    @InjectMocks
    private RestauranteServicioImpl restauranteServicio;

    private Restaurante restaurante;
    private RestauranteDTO restauranteDTO;
    private Platillo platillo;
    private PlatilloDTO platilloDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurante = new Restaurante(
                "123456",
                "Restaurante Ejemplo",
                "Calle 123 #45-67",
                "3101234567",
                "3109876543",
                Categoria.COLOMBIANA,
                "imagen_url",
                "Un restaurante de prueba",
                new ArrayList<>()
        );

        restauranteDTO = new RestauranteDTO();
        platillo = new Platillo();
        platilloDTO = new PlatilloDTO();

        platillo.setId("1");
        platilloDTO.setId("1");
        platilloDTO.setCantidadDisponible(10);
    }


    @Test
    void testRegistrarRestaurante() throws EcoappetiteException {
        when(restauranteMapper.toDomain(restauranteDTO)).thenReturn(restaurante);
        when(restauranteRepositorio.registrarRestaurante(restaurante)).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.registrarRestaurante(restauranteDTO);

        assertEquals(restauranteDTO, result);
        verify(restauranteRepositorio, times(1)).registrarRestaurante(restaurante);
    }

    @Test
    void testConsultarTodosLosRestaurantes() {
        when(restauranteRepositorio.consultarTodosLosRestaurantes()).thenReturn(Collections.singletonList(restaurante));
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        List<RestauranteDTO> result = restauranteServicio.consultarTodosLosRestaurantes();

        assertEquals(1, result.size());
        assertEquals(restauranteDTO, result.get(0));
        verify(restauranteRepositorio, times(1)).consultarTodosLosRestaurantes();
    }

    @Test
    void testConsultarRestaurantePorId() throws EcoappetiteException {
        when(restauranteRepositorio.consultarRestaurantePorId("1")).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.consultarRestaurantePorId("1");

        assertEquals(restauranteDTO, result);
        verify(restauranteRepositorio, times(1)).consultarRestaurantePorId("1");
    }

    @Test
    void testAgregarPlatilloRestaurante() throws EcoappetiteException {
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
        when(restauranteRepositorio.existePlatillo("Restaurante1", platillo)).thenReturn(false);
        when(platilloServicio.agregarPlatillo(platilloDTO)).thenReturn(platilloDTO);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);

        restauranteServicio.agregarPlatilloRestaurante("Restaurante1", platilloDTO);

        verify(restauranteRepositorio, times(1)).agregarPlatilloRestaurante("Restaurante1", platillo);
    }

    @Test
    void testModificarCantidadPlatilloRestaurante() throws EcoappetiteException {
        when(platilloServicio.consultarPlatilloPorId("1")).thenReturn(platilloDTO);
        when(restauranteRepositorio.consultarRestaurantePorId("1")).thenReturn(restaurante);
        when(restauranteMapper.toDTO(restaurante)).thenReturn(restauranteDTO);

        RestauranteDTO result = restauranteServicio.modificarCantidadPlatilloRestaurante("1", "1", 5);

        assertEquals(restauranteDTO, result);
        verify(restauranteRepositorio, times(1)).consultarRestaurantePorId("1");
    }
}