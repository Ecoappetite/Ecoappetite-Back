package co.edu.eci.ecoappetite.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping(value = "")
    public ResponseEntity<List<RestauranteDTO>> consultarTodosLosRestaurantes(){
        var restaurantes = restauranteServicio.consultarTodosLosRestaurantes();
        return ResponseEntity.status(200).body(restaurantes);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<RestauranteDTO> consultarRestaurantePorId(@PathVariable("id") String id) throws EcoappetiteException{
        var restaurante = restauranteServicio.consultarRestaurantePorId(id);
        return ResponseEntity.status(200).body(restaurante);
    }

    @GetMapping(value = "/nombre/{nombre}")
    public ResponseEntity<RestauranteDTO> consultarRestaurantePorNombre(@PathVariable("nombre") String nombre) throws EcoappetiteException{
        var restaurante = restauranteServicio.consultarRestaurantePorNombre(nombre);
        return ResponseEntity.status(200).body(restaurante);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> modificarRestaurante(@PathVariable("id") String id, @RequestBody RestauranteDTO restauranteDTO) throws EcoappetiteException{
        restauranteServicio.modificarRestaurante(id, restauranteDTO);
        return ResponseEntity.status(201).body("El restaurante: "+ restauranteDTO.getNombre() + " ha sido modificado.");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> eliminarRestaurante(@PathVariable("id") String id) throws EcoappetiteException{
        restauranteServicio.eliminarRestaurante(id);
        return ResponseEntity.status(200).body("El restaurante: "+ id + " ha sido eliminado.");

    }




    
    
}
