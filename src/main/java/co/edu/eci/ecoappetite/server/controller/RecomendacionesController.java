package co.edu.eci.ecoappetite.server.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.motor.MotorRecomendacion;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Secured("CONSUMIDOR")
@RequestMapping(value = "/recomendacion")
public class RecomendacionesController {

    private final MotorRecomendacion motorRecomendacion;

    @GetMapping(value = "/{idConsumidor}")
    public ResponseEntity<String> recomendacionDeLaIa (@PathVariable("idConsumidor") String idConsumidor) throws EcoappetiteException{
        String mensaje = motorRecomendacion.mensajeDevueltoPorIa(idConsumidor);
        return ResponseEntity.status(200).body(mensaje);
    }

    @PostMapping(value = "/{idConsumidor}")
    public ResponseEntity<String> insertarRecomendacionIa (@PathVariable("idConsumidor") String idConsumidor, @RequestBody Map<String, String> payload) throws EcoappetiteException{
        String infRestaurante = payload.getOrDefault("restaurante", null);
        String infPlatillo = payload.getOrDefault("platillo", null);
         
        if (infRestaurante == null && infPlatillo == null) {
            throw new DataValidationException("Sin información de registro");
        }

        motorRecomendacion.inyectarRecomendacion(idConsumidor, infRestaurante, infPlatillo);

        return ResponseEntity.status(200).body("Recomendacion registrada");
    }


    @GetMapping(value = "")
    public ResponseEntity<List<RegistroHistorial>> consultarTodasLasRecomendaciones(){
        var registroHistorial = motorRecomendacion.consultarTodasLasRecomendaciones();
        return ResponseEntity.status(200).body(registroHistorial);
    }
    
}
