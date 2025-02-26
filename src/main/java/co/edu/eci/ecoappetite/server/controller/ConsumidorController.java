package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumidor")
public class ConsumidorController {
    @Autowired
    private ConsumidorServicio consumidorServicio;

    @PostMapping
    public ResponseEntity<String> resgistrarConsumidor(@RequestBody ConsumidorDTO consumidorDTO) throws EcoappetiteException {
        consumidorServicio.registrarConsumidor(consumidorDTO);
        return ResponseEntity.status(201).body("Consumidor registrado con éxito");
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<ConsumidorDTO> consultarConsumidorPorId(@PathVariable("id") String id) {
        try {
            var consumidor = consumidorServicio.consultarConsumidorPorId(id);
            return ResponseEntity.status(HttpStatus.OK).body(consumidor);
        } catch (Exception e) {
            return (ResponseEntity<ConsumidorDTO>) BadRequest.manejarErrores(e);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> modificarConsumidor(@PathVariable("id") String id, @RequestBody ConsumidorDTO consumidorDTO) {
        try {
            consumidorServicio.modificarConsumidor(id, consumidorDTO);
            return ResponseEntity.status(HttpStatus.OK).body("El consumidor: " + consumidorDTO.getNombre() + " ha sido modificado");
        } catch (Exception e) {
            return (ResponseEntity<String>) BadRequest.manejarErrores(e);
        }
    }




}

