package co.edu.eci.ecoappetite.server.controller.advisor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String GENERAL_ERROR = "Internal server error";

    @ExceptionHandler
    public ResponseEntity<String> handlerException(Exception exception){
        return ResponseEntity.internalServerError().body(GENERAL_ERROR);

    } 
    

}
