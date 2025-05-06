package co.edu.eci.ecoappetite.server.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.model.CategoriaPlatillo;
import co.edu.eci.ecoappetite.server.domain.model.EstadoPlatillo;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.repository.PlatilloRepositorio;

@ExtendWith(MockitoExtension.class)
class PlatilloServicioImplTest {
    @Mock
    private PlatilloRepositorio platilloRepositorio;
    
    @Mock
    private PlatilloMapper platilloMapper;
    
    @InjectMocks
    private PlatilloServicioImpl platilloServicio;

    private PlatilloDTO platilloDTO;
    private Platillo platillo;

    @BeforeEach
    void setUp() {
        platilloDTO = new PlatilloDTO(
            "Platillo2", 
            "Pizza Margarita", 
            25000.0, 
            20000.0, 
            CategoriaPlatillo.COMIDA_RAPIDA, 
            EstadoPlatillo.DISPONIBLE, 
            10, 
            "https://ejemplo.com/pizza-margarita.jpg", 
            "Deliciosa pizza con tomate, albahaca y queso mozzarella.", 
            "12345678"
        );

        platillo = new Platillo(
            "Platillo2", 
            "Pizza Margarita", 
            25000.0, 
            20000.0, 
            CategoriaPlatillo.COMIDA_RAPIDA, 
            EstadoPlatillo.DISPONIBLE, 
            10, 
            "https://ejemplo.com/pizza-margarita.jpg", 
            "Deliciosa pizza con tomate, albahaca y queso mozzarella.", 
            "12345678"
        );
    }

    @Test
    void agregarPlatillo_deberiaAgregarYDevolverPlatilloDTO() throws EcoappetiteException {
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
        when(platilloRepositorio.agregarPlatillo(platillo)).thenReturn(platillo);
        when(platilloMapper.toDTO(platillo)).thenReturn(platilloDTO);

        PlatilloDTO resultado = platilloServicio.agregarPlatillo(platilloDTO);

        assertNotNull(resultado);
        assertEquals("Pizza Margarita", resultado.getNombre());
    }

    @Test
    void agregarPlatillo_conCampoNulo_deberiaLanzarExcepcion() {
        assertThrows(NullPointerException.class, () -> new PlatilloDTO("1", null, 20000.0, 18000.0, CategoriaPlatillo.PLATO_FUERTE, EstadoPlatillo.DISPONIBLE, 10, "imagen.jpg", "Pizza de queso", "123456"));
    }

    @Test
    void consultarTodosLosPlatillos_deberiaRetornarListaDePlatilloDTO() {
        List<Platillo> platillos = Arrays.asList(platillo);
        when(platilloRepositorio.consultarTodosLosPlatillosos()).thenReturn(platillos);
        when(platilloMapper.toDTO(platillo)).thenReturn(platilloDTO);
        
        List<PlatilloDTO> resultado = platilloServicio.consultarTodosLosPlatillos();
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Pizza Margarita", resultado.get(0).getNombre());
    }

    @Test
    void consultarPlatilloPorId_deberiaRetornarPlatilloDTO() throws EcoappetiteException {
        when(platilloRepositorio.consultarPlatilloPorId("1")).thenReturn(platillo);
        when(platilloMapper.toDTO(platillo)).thenReturn(platilloDTO);

        PlatilloDTO resultado = platilloServicio.consultarPlatilloPorId("1");
        
        assertNotNull(resultado);
        assertEquals("Pizza Margarita", resultado.getNombre());
    }

    @Test
    void consultarPlatilloPorNombre_deberiaRetornarListaPlatilloDTO() throws EcoappetiteException {
        List<Platillo> platillos = Arrays.asList(platillo);
        when(platilloRepositorio.consultarPlatilloPorNombre("Pizza Margarita")).thenReturn(platillos);
        when(platilloMapper.toDTO(platillo)).thenReturn(platilloDTO);
        
        List<PlatilloDTO> resultado = platilloServicio.consultarPlatilloPorNombre("Pizza Margarita");
        
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Pizza Margarita", resultado.get(0).getNombre());
    }

    @Test
    void modificarPlatillo_deberiaModificarYDevolverPlatilloDTO() throws EcoappetiteException {
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
        when(platilloRepositorio.modificarPlatillo("1", platillo)).thenReturn(platillo);
        when(platilloMapper.toDTO(platillo)).thenReturn(platilloDTO);

        PlatilloDTO resultado = platilloServicio.modificarPlatillo("1", platilloDTO);

        assertNotNull(resultado);
        assertEquals("Pizza Margarita", resultado.getNombre());
    }

    @Test
    void eliminarPlatillo_deberiaEliminarPlatilloSinErrores() throws EcoappetiteException {
        doNothing().when(platilloRepositorio).eliminarPlatillo("1");
        
        assertDoesNotThrow(() -> platilloServicio.eliminarPlatillo("1"));
        verify(platilloRepositorio, times(1)).eliminarPlatillo("1");
    }   

    @Test
    void eliminarPlatilloPorNitRestaurante_deberiaEliminarSinErrores() throws EcoappetiteException {
        doNothing().when(platilloRepositorio).eliminarPlatilloPorNitRestaurante("12345678");

        assertDoesNotThrow(() -> platilloServicio.eliminarPlatilloPorNitRestaurante("12345678"));
        verify(platilloRepositorio, times(1)).eliminarPlatilloPorNitRestaurante("12345678");
    }

    @Test
    void consultarPlatilloPorId_cuandoNoExiste_deberiaLanzarExcepcion() throws EcoappetiteException {
        when(platilloRepositorio.consultarPlatilloPorId("999"))
            .thenThrow(new EcoappetiteException("No encontrado"));

        EcoappetiteException ex = assertThrows(
            EcoappetiteException.class,
            () -> platilloServicio.consultarPlatilloPorId("999")
        );

        assertEquals("No encontrado", ex.getMessage());
    }

    @Test
    void agregarPlatillo_conDescuentoMayorQueOriginal_lanzaExcepcion() {
        platillo.setPrecioOriginal(15000.0);
        platillo.setPrecioDescuento(20000.0);
        platilloDTO.setPrecioOriginal(15000.0);
        platilloDTO.setPrecioDescuento(20000.0);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
    
        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }
    
    @Test
    void agregarPlatillo_conEstadoAgotadoYCantidadDisponibleMayorACero_lanzaExcepcion() {
        platillo.setEstadoPlatillo(EstadoPlatillo.AGOTADO);
        platillo.setCantidadDisponible(5);
        platilloDTO.setEstadoPlatillo(EstadoPlatillo.AGOTADO);
        platilloDTO.setCantidadDisponible(5);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
    
        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }
    
    @Test
    void agregarPlatillo_conEstadoDisponibleYCantidadCero_lanzaExcepcion() {
        platillo.setEstadoPlatillo(EstadoPlatillo.DISPONIBLE);
        platillo.setCantidadDisponible(0);
        platilloDTO.setEstadoPlatillo(EstadoPlatillo.DISPONIBLE);
        platilloDTO.setCantidadDisponible(0);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
    
        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }
    
    @Test
    void agregarPlatillo_conNombreVacio_lanzaExcepcion() {
        platillo.setNombre("");
        platilloDTO.setNombre("");
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
    
        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }
    
    @Test
    void agregarPlatillo_conPrecioOriginalCero_lanzaExcepcion() {
        platillo.setPrecioOriginal(0.0);
        platilloDTO.setPrecioOriginal(0.0);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
    
        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }
    
    @Test
    void agregarPlatillo_conCantidadNegativa_lanzaExcepcion() {
        platillo.setCantidadDisponible(-3);
        platilloDTO.setCantidadDisponible(-3);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
    
        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }

    @Test
    void modificarPlatillo_conPrecioDescuentoMayorQueOriginal_lanzaExcepcion() {
        platillo.setPrecioOriginal(15000.0);
        platillo.setPrecioDescuento(20000.0);
        platilloDTO.setPrecioOriginal(15000.0);
        platilloDTO.setPrecioDescuento(20000.0);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);

        assertThrows(DataValidationException.class, () -> platilloServicio.modificarPlatillo("1", platilloDTO));
    }

    @Test
    void modificarPlatillo_inexistente_lanzaExcepcion() throws EcoappetiteException {
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
        when(platilloRepositorio.modificarPlatillo(eq("999"), any())).thenThrow(new EcoappetiteException("No existe"));

        assertThrows(EcoappetiteException.class, () -> platilloServicio.modificarPlatillo("999", platilloDTO));
    }

    @Test
    void agregarPlatillo_verificaInteraccionConMapperYRepositorio() throws EcoappetiteException {
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);
        when(platilloRepositorio.agregarPlatillo(platillo)).thenReturn(platillo);
        when(platilloMapper.toDTO(platillo)).thenReturn(platilloDTO);

        platilloServicio.agregarPlatillo(platilloDTO);

        verify(platilloMapper).toDomain(platilloDTO);
        verify(platilloRepositorio).agregarPlatillo(platillo);
        verify(platilloMapper).toDTO(platillo);
    }

    @Test
    void agregarPlatillo_conImagenVacia_lanzaExcepcion() {
        platillo.setImagen("");
        platilloDTO.setImagen("");
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);

        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }

    @Test
    void agregarPlatillo_conDescripcionVacia_lanzaExcepcion() {
        platillo.setDescripcion("");
        platilloDTO.setDescripcion("");
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);

        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }

    @Test
    void agregarPlatillo_conPrecioDescuentoCero_lanzaExcepcion() {
        platillo.setPrecioDescuento(0.0);
        platilloDTO.setPrecioDescuento(0.0);
        when(platilloMapper.toDomain(platilloDTO)).thenReturn(platillo);

        assertThrows(DataValidationException.class, () -> platilloServicio.agregarPlatillo(platilloDTO));
    }


    
}
