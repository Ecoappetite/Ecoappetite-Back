package co.edu.eci.ecoappetite.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.RestauranteServicio;

@RestController
@RequestMapping(value = "/restaurante")
public class RestauranteController {

    private final RestauranteServicio restauranteServicio;

    @Autowired
    public RestauranteController(RestauranteServicio restauranteServicio){
        this.restauranteServicio = restauranteServicio;

    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> registrarRestaurante(@RequestBody RestauranteDTO restauranteDTO) throws EcoappetiteException{
        restauranteServicio.registrarRestaurante(restauranteDTO);        
        return ResponseEntity.status(201).body("Restaurante :" + restauranteDTO.getNombre() + " registrado");
        
    }


    
    
}
