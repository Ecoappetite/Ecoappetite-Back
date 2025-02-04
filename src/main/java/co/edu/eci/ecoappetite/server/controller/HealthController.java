package co.edu.eci.ecoappetite.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/health")
public class HealthController {

    private static final String HEALTH_CHECK_MESSAGE = "API OK";

    @GetMapping
    public ResponseEntity<String> getHealth(){
        return ResponseEntity.ok(HEALTH_CHECK_MESSAGE);
    }

    @GetMapping(value = "/bad")
    public ResponseEntity<String> getHealthBad(){
        throw new UnsupportedOperationException();
    }


}
