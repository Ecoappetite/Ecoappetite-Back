package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.ConsumidorMapper;
import co.edu.eci.ecoappetite.server.repository.ConsumidorRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

// Unit tests with mocks
@ExtendWith(MockitoExtension.class)
class ConsumidorServicioImplTest {

    @Mock
    private ConsumidorRepositorio consumidorRepositorio;

    @Mock
    private ConsumidorMapper consumidorMapper;

    @InjectMocks
    private ConsumidorServicioImpl consumidorServicio;

    private ConsumidorDTO consumidorDTO;
    private Consumidor consumidor;

    @BeforeEach
    void setUp() {
        // Configurar datos de prueba
        consumidorDTO = new ConsumidorDTO();
        consumidorDTO.setId("123");
        consumidorDTO.setNombre("Juan Pérez");
        consumidorDTO.setEmail("juan@ejemplo.com");
        consumidorDTO.setTelefono("1234567890");
        consumidorDTO.setDireccion("Calle 123");
        consumidorDTO.setPreferencias("Vegetariano");

        consumidor = new Consumidor(
                "123",
                "Juan Pérez",
                "juan@ejemplo.com",
                "1234567890",
                "Calle 123",
                "Vegetariano"
        );
    }

    @Test
    void testRegistrarConsumidor() throws EcoappetiteException {
        // Arrange
        when(consumidorMapper.toDomain(any(ConsumidorDTO.class))).thenReturn(consumidor);
        when(consumidorRepositorio.registrarConsumidor(any(Consumidor.class))).thenReturn(consumidor);
        when(consumidorMapper.toDTO(any(Consumidor.class))).thenReturn(consumidorDTO);

        // Act
        ConsumidorDTO resultado = consumidorServicio.registrarConsumidor(consumidorDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(consumidorDTO.getId(), resultado.getId());
        assertEquals(consumidorDTO.getNombre(), resultado.getNombre());
        verify(consumidorMapper).toDomain(consumidorDTO);
        verify(consumidorRepositorio).registrarConsumidor(consumidor);
        verify(consumidorMapper).toDTO(consumidor);
    }

    @Test
    void testEliminarConsumidor() throws EcoappetiteException {
        // Act
        consumidorServicio.eliminarConsumidor("123");

        // Assert
        verify(consumidorRepositorio).eliminarConsumidor("123");
    }

    @Test
    void testConsultarConsumidorPorId() throws EcoappetiteException {
        // Arrange
        when(consumidorRepositorio.consultarConsumidorPorId(anyString())).thenReturn(consumidor);
        when(consumidorMapper.toDTO(any(Consumidor.class))).thenReturn(consumidorDTO);

        // Act
        ConsumidorDTO resultado = consumidorServicio.consultarConsumidorPorId("123");

        // Assert
        assertNotNull(resultado);
        assertEquals("123", resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(consumidorRepositorio).consultarConsumidorPorId("123");
        verify(consumidorMapper).toDTO(consumidor);
    }

    @Test
    void testModificarConsumidor() throws EcoappetiteException {
        // Arrange
        when(consumidorMapper.toDomain(any(ConsumidorDTO.class))).thenReturn(consumidor);
        when(consumidorRepositorio.modificarConsumidor(anyString(), any(Consumidor.class))).thenReturn(consumidor);
        when(consumidorMapper.toDTO(any(Consumidor.class))).thenReturn(consumidorDTO);

        // Act
        ConsumidorDTO resultado = consumidorServicio.modificarConsumidor("123", consumidorDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals("123", resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(consumidorMapper).toDomain(consumidorDTO);
        verify(consumidorRepositorio).modificarConsumidor("123", consumidor);
        verify(consumidorMapper).toDTO(consumidor);
    }
}
