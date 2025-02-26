package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarConsumidor(@PathVariable("id") String id) throws EcoappetiteException {
        consumidorServicio.eliminarConsumidor(id);
        return ResponseEntity.status(200).body("El consumidor con ID " + id + " ha sido eliminado.");
    }
}
