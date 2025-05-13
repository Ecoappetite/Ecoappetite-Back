package co.edu.eci.ecoappetite.server.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.PlatilloServicio;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/platillo")
@Secured({"RESTAURANTE", "CONSUMIDOR"})
public class PlatilloController {

    private final PlatilloServicio platilloServicio;
    
    @GetMapping(value = "")
    public ResponseEntity<List<PlatilloDTO>> consultarTodosLosPlatillos(){
        var platillos = platilloServicio.consultarTodosLosPlatillos();
        return ResponseEntity.status(200).body(platillos);
    }


    @Secured("RESTAURANTE")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PlatilloDTO> consultarPlatilloPorId(@PathVariable("id") String id) throws EcoappetiteException{
        var platillo = platilloServicio.consultarPlatilloPorId(id);
        return ResponseEntity.status(200).body(platillo);
    }

    @GetMapping(value = "/nombre/{nombre}")
    public ResponseEntity<List<PlatilloDTO>> consultarPlatilloPorNombre(@PathVariable("nombre") String nombre) throws EcoappetiteException{
        var platillo = platilloServicio.consultarPlatilloPorNombre(nombre);
        return ResponseEntity.status(200).body(platillo);
    }

}
