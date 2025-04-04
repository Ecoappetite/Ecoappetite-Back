package co.edu.eci.ecoappetite.server.controller.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Contenido público.";
    }

    @GetMapping("/user") //petición HTTP GET a la URL
    //Solo permite acceder a este endpoint si el usuario autenticado tiene el rol CONSUMIDOR o RESTAURANTE
    @PreAuthorize("hasRole('CONSUMIDOR') or hasRole('RESTAURANTE')")
    public String userAccess() {
        return "Contenido para consumidores o restaurantes.";
    }

    @GetMapping("/solo-consumidor")
    @PreAuthorize("hasRole('CONSUMIDOR')")
    public String soloConsumidorAccess() {
        return "Contenido exclusivo para consumidores.";
    }

    @GetMapping("/solo-restaurante")
    @PreAuthorize("hasRole('RESTAURANTE')")
    public String soloRestauranteAccess(){
        return "Contenido exclusivo para restaurantes.";
    }
}