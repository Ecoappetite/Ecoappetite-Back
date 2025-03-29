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

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.RestauranteServicio;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteServicio restauranteServicio;

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

    @GetMapping(value = "/{id}")
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

    @DeleteMapping(value = "/{nit}")
    public ResponseEntity<String> eliminarRestaurante(@PathVariable("nit") String nit) throws EcoappetiteException{
        restauranteServicio.eliminarRestaurante(nit);
        return ResponseEntity.status(200).body("El restaurante: "+ nit + " ha sido eliminado.");

    }

    @PutMapping(value = "/{nombre}/platillo")
    public ResponseEntity<String> agregarPlatilloRestaurante(@PathVariable("nombre") String nombre, @RequestBody PlatilloDTO platilloDTO) throws EcoappetiteException{
        restauranteServicio.agregarPlatilloRestaurante(nombre,platilloDTO);
        return ResponseEntity.status(200).body("El platillo: "+ platilloDTO.getNombre() + " ha sido agregado al restaurante.");
    } 

    @DeleteMapping(value = "/{nit}/platillo/{idPlatillo}")
    public ResponseEntity<String> eliminarPlatilloRestaurante(@PathVariable("nit") String nit, @PathVariable("idPlatillo") String idPlatillo) throws EcoappetiteException{
        restauranteServicio.eliminarPlatilloRestaurante(nit, idPlatillo);
        return ResponseEntity.status(200).body("El platillo: " + idPlatillo + " ha sido eliminado");
    }
    
}
