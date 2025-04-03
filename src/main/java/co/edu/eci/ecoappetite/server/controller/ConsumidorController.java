package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import co.edu.eci.ecoappetite.server.service.RecomendacionServicio;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consumidor")
public class ConsumidorController {

    private final ConsumidorServicio consumidorServicio;
    private final RecomendacionServicio recomendacionServicio;

    @PostMapping
    public ResponseEntity<String> resgistrarConsumidor(@RequestBody ConsumidorDTO consumidorDTO) throws EcoappetiteException {
        consumidorServicio.registrarConsumidor(consumidorDTO);
        return ResponseEntity.status(201).body("Consumidor registrado con éxito");
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ConsumidorDTO> consultarConsumidorPorId(@PathVariable("id") String id) throws EcoappetiteException{
        var consumidor = consumidorServicio.consultarConsumidorPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(consumidor);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> modificarConsumidor(@PathVariable("id") String id, @RequestBody ConsumidorDTO consumidorDTO) throws EcoappetiteException{
        consumidorServicio.modificarConsumidor(id, consumidorDTO);
        return ResponseEntity.status(HttpStatus.OK).body("El consumidor: " + consumidorDTO.getNombre() + " ha sido modificado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarConsumidor(@PathVariable("id") String id) throws EcoappetiteException {
        consumidorServicio.eliminarConsumidor(id);
        return ResponseEntity.status(200).body("El consumidor con ID " + id + " ha sido eliminado.");
    }

    @GetMapping("/{id}/recomendaciones")
    public ResponseEntity<List<RestauranteDTO>> recomendarRestaurante(@PathVariable("id") String id) throws EcoappetiteException{
        return ResponseEntity.ok(recomendacionServicio.recomendarRestaurante(id));
    }

    @GetMapping("/{id}/recomendaciones/platillos")
    public ResponseEntity<List<PlatilloDTO>> recomendarPlatillos(@PathVariable("id") String id) throws EcoappetiteException{
        return ResponseEntity.ok(recomendacionServicio.recomendarPlatillo(id));
    }

}

