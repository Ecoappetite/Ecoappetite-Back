package co.edu.eci.ecoappetite.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.motor.MotorRecomendacion;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/recomendacion")
public class RecomendacionesController {

    private final MotorRecomendacion motorRecomendacion;

    @GetMapping(value = "/{idConsumidor}")
    public ResponseEntity<String> recomendacionDeLaIa (@PathVariable("idConsumidor") String idConsumidor) throws EcoappetiteException{
        String mensaje = motorRecomendacion.mensajeDevueltoPorIa(idConsumidor);
        return ResponseEntity.status(200).body(mensaje);
    }


    
}
