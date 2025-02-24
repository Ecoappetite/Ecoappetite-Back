package co.edu.eci.ecoappetite.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping(value = "")
    public ResponseEntity<List<PlatilloDTO>> consultarTodosLosPlatillos(){
        var platillos = platilloServicio.consultarTodosLosPlatillos();
        return ResponseEntity.status(200).body(platillos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PlatilloDTO> consultarPlatilloPorId(@PathVariable("id") String id) throws EcoappetiteException{
        var platillo = platilloServicio.consultarPlatilloPorId(id);
        return ResponseEntity.status(200).body(platillo);
    }

    @GetMapping(value = "/{nombre}")
    public ResponseEntity<List<PlatilloDTO>> consultarPlatilloPorNombre(@PathVariable("nombre") String nombre) throws EcoappetiteException{
        var platillo = platilloServicio.consultarPlatilloPorNombre(nombre);
        return ResponseEntity.status(200).body(platillo);
    }
}
