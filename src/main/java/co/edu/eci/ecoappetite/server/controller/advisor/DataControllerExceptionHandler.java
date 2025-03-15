package co.edu.eci.ecoappetite.server.controller.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.MessageException;

@ControllerAdvice
public class DataControllerExceptionHandler {

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<String> handlerException(DataValidationException dataValidationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageException.DATA_VALIDATION_ERROR.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handlerException(NullPointerException nullPointerException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageException.DATA_VALIDATION_ERROR.getMessage());
    }
    
}
