package co.edu.eci.ecoappetite.server.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.dto.UsuarioDTO;
import co.edu.eci.ecoappetite.server.domain.model.Rol;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import co.edu.eci.ecoappetite.server.service.RestauranteServicio;
import co.edu.eci.ecoappetite.server.service.UsuarioServicio;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServicio usuarioServicio;
    private final ConsumidorServicio consumidorServicio;
    private final RestauranteServicio restauranteServicio;

    @PostMapping(value = "/registro/consumidor")
    public ResponseEntity<String> registrarConsumidor(@RequestBody Map<String, Object> payload) throws EcoappetiteException{
        ObjectMapper mapper = new ObjectMapper();
        UsuarioDTO usuarioDTO = mapper.convertValue(payload.get("usuario"), UsuarioDTO.class);
        ConsumidorDTO consumidorDTO = mapper.convertValue(payload.get("consumidor"), ConsumidorDTO.class);
        usuarioDTO.setRol(Rol.CONSUMIDOR);
        usuarioDTO.setIdEntidadAlmacenada(consumidorDTO.getId());
        usuarioServicio.registrarUsuario(usuarioDTO);
        consumidorServicio.registrarConsumidor(consumidorDTO);
        return ResponseEntity.status(201).body("Consumidor registrado");  
    }

    @PostMapping(value = "/registro/restaurante")
    public ResponseEntity<String> registrarRestaurante(@RequestBody Map<String, Object> payload) throws EcoappetiteException{
        ObjectMapper mapper = new ObjectMapper();
        UsuarioDTO usuarioDTO = mapper.convertValue(payload.get("usuario"), UsuarioDTO.class);
        RestauranteDTO restauranteDTO = mapper.convertValue(payload.get("restaurante"), RestauranteDTO.class);
        usuarioDTO.setRol(Rol.RESTAURANTE);
        usuarioDTO.setIdEntidadAlmacenada(restauranteDTO.getNit());
        usuarioServicio.registrarUsuario(usuarioDTO);
        restauranteServicio.registrarRestaurante(restauranteDTO);
        return ResponseEntity.status(201).body("Restaurante registrado");  
    }
    
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) throws EcoappetiteException{
        String token = usuarioServicio.login(usuarioDTO);
        return ResponseEntity.status(200).body(token);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody UsuarioDTO usuarioDTO) throws EcoappetiteException{
        usuarioServicio.logout(usuarioDTO);
        return ResponseEntity.status(200).body("Logout exitoso");
    }
}
