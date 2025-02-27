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
        consumidorDTO = new ConsumidorDTO("123", "Juan Pérez", "juan@ejemplo.com", "1234567890", "Calle 123", "Vegetariano");
    }

    @Test
    void testConsultarConsumidorPorId_Success() throws EcoappetiteException {
        when(consumidorServicio.consultarConsumidorPorId(anyString())).thenReturn(consumidorDTO);
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("123");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consumidorDTO, response.getBody());
        verify(consumidorServicio, times(1)).consultarConsumidorPorId("123");
    }

    @Test
    void testConsultarConsumidorPorId_ThrowsException() throws EcoappetiteException {
        when(consumidorServicio.consultarConsumidorPorId(anyString())).thenThrow(new EcoappetiteException("Consumidor no encontrado"));
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("999");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Consumidor no encontrado", response.getBody());
        verify(consumidorServicio, times(1)).consultarConsumidorPorId("999");
    }

    @Test
    void testConsultarConsumidorPorId_InvalidId() throws EcoappetiteException {
        when(consumidorServicio.consultarConsumidorPorId("invalid")).thenThrow(new EcoappetiteException("ID de consumidor inválido"));
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("invalid");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: ID de consumidor inválido", response.getBody());
        verify(consumidorServicio, times(1)).consultarConsumidorPorId("invalid");
    }

    @Test
    void testConsultarConsumidorPorId_NotFound() throws EcoappetiteException {
        when(consumidorServicio.consultarConsumidorPorId("456")).thenThrow(new EcoappetiteException("Consumidor no encontrado"));
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("456");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Consumidor no encontrado", response.getBody());
        verify(consumidorServicio, times(1)).consultarConsumidorPorId("456");
    }



    @Test
    void testConsultarConsumidorPorId_LongId() throws EcoappetiteException {
        String longId = "123456789012345678901234567890";
        when(consumidorServicio.consultarConsumidorPorId(longId)).thenReturn(consumidorDTO);
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId(longId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consumidorDTO, response.getBody());
    }

    @Test
    void testConsultarConsumidorPorId_SpecialCharacters() throws EcoappetiteException {
        String specialId = "@#*!&";
        when(consumidorServicio.consultarConsumidorPorId(specialId)).thenThrow(new EcoappetiteException("ID de consumidor inválido"));
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId(specialId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: ID de consumidor inválido", response.getBody());
    }

    
    @Test
    void testConsultarConsumidorPorId_NumericId() throws EcoappetiteException {
        when(consumidorServicio.consultarConsumidorPorId("987654321")).thenReturn(consumidorDTO);
        ResponseEntity<ConsumidorDTO> response = consumidorController.consultarConsumidorPorId("987654321");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(consumidorDTO, response.getBody());
    }
}
