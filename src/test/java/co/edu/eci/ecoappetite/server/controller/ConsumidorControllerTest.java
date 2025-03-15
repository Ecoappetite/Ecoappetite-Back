package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConsumidorControllerTest {

    @Mock
    private ConsumidorServicio consumidorServicio;

    @InjectMocks
    private ConsumidorController consumidorController;

    private ConsumidorDTO consumidorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consumidorDTO = new ConsumidorDTO();
        consumidorDTO.setId("1234");
        consumidorDTO.setNombre("Juan Pérez");
        consumidorDTO.setEmail("juan@ejemplo.com");
        consumidorDTO.setTelefono("1234567890");
        consumidorDTO.setDireccion("Calle 123");
        consumidorDTO.setPreferencias("Vegetariano");
    }

    @Test
    void testRegistrarConsumidor_Success() throws EcoappetiteException {
        // No need for doNothing() since we're not stubbing a void method

        // Act
        ResponseEntity<String> response = consumidorController.resgistrarConsumidor(consumidorDTO);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Consumidor registrado con éxito", response.getBody());
        verify(consumidorServicio, times(1)).registrarConsumidor(consumidorDTO);
    }

    @Test
    void testRegistrarConsumidor_ThrowsException() throws EcoappetiteException {
        // Arrange
        doThrow(new EcoappetiteException("Error al registrar consumidor")).when(consumidorServicio).registrarConsumidor(any(ConsumidorDTO.class));

        // Act & Assert
        assertThrows(EcoappetiteException.class, () -> {
            consumidorController.resgistrarConsumidor(consumidorDTO);
        });
        verify(consumidorServicio, times(1)).registrarConsumidor(consumidorDTO);
    }

    @Test
    void testConsultarConsumidorPorId_Success() throws EcoappetiteException {
        // Arrange
        when(consumidorServicio.consultarConsumidorPorId(anyString())).thenReturn(consumidorDTO);

        // Act
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consumidorDTO, response.getBody());
        verify(consumidorServicio, times(1)).consultarConsumidorPorId("123");
    }

    @Test
    void testConsultarConsumidorPorId_ThrowsException() throws EcoappetiteException {
        // Arrange
        when(consumidorServicio.consultarConsumidorPorId(anyString())).thenThrow(new EcoappetiteException("Consumidor no encontrado"));

        // Act
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("999");

        // Assert
        assertEquals("Error: Consumidor no encontrado", response.getBody());
        verify(consumidorServicio, times(1)).consultarConsumidorPorId("999");
    }

    @Test
    void testModificarConsumidor_Success() throws EcoappetiteException {
        // Don't use doNothing() for non-void methods

        // Act
        ResponseEntity<String> response = consumidorController.modificarConsumidor("123", consumidorDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("El consumidor: " + consumidorDTO.getNombre() + " ha sido modificado", response.getBody());
        verify(consumidorServicio, times(1)).modificarConsumidor("123", consumidorDTO);
    }

    @Test
    void testModificarConsumidor_ThrowsException() throws EcoappetiteException {
        // Arrange
        doThrow(new EcoappetiteException("Error al modificar consumidor")).when(consumidorServicio)
                .modificarConsumidor(anyString(), any(ConsumidorDTO.class));

        // Act
        ResponseEntity<String> response = consumidorController.modificarConsumidor("123", consumidorDTO);

        // Assert
        assertEquals("Error: Error al modificar consumidor", response.getBody());
        verify(consumidorServicio, times(1)).modificarConsumidor("123", consumidorDTO);
    }

    @Test
    void testEliminarConsumidor_Success() throws EcoappetiteException {
        // Act
        ResponseEntity<String> response = consumidorController.eliminarConsumidor("123");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("El consumidor con ID 123 ha sido eliminado.", response.getBody());
        verify(consumidorServicio, times(1)).eliminarConsumidor("123");
    }

    @Test
    void testEliminarConsumidor_ThrowsException() throws EcoappetiteException {
        // Arrange
        doThrow(new EcoappetiteException("Error al eliminar consumidor")).when(consumidorServicio)
                .eliminarConsumidor(anyString());

        // Act
        ResponseEntity<String> response = consumidorController.eliminarConsumidor("123");

        // Assert
        assertEquals("Error: Error al eliminar consumidor", response.getBody());
        verify(consumidorServicio, times(1)).eliminarConsumidor("123");
    }
}