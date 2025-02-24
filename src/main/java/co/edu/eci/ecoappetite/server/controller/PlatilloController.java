package co.edu.eci.ecoappetite.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.PlatilloServicio;


@RestController
@RequestMapping(value = "/platillo")
public class PlatilloController {

    private final PlatilloServicio platilloServicio;

    @Autowired
    public PlatilloController(PlatilloServicio platilloServicio){
        this.platilloServicio = platilloServicio;

    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> agregarPlatillo(@RequestBody PlatilloDTO platilloDTO) throws EcoappetiteException{
        platilloServicio.agregarPlatillo(platilloDTO);        
        return ResponseEntity.status(201).body("Platillo :" + platilloDTO.getNombre() + " agregado");
        
    }
    
}
