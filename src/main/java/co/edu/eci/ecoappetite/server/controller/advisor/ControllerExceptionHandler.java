package co.edu.eci.ecoappetite.server.controller.advisor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import org.springframework.dao.DuplicateKeyException;

import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.MessageException;

import co.edu.eci.ecoappetite.server.exception.NotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerException(Exception exception){
        exception.printStackTrace();
        return ResponseEntity.internalServerError().body(MessageException.GENERAL_ERROR.getMessage());
    } 
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerException(NotFoundException notFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageException.NOT_FOUND_ERROR.getMessage() + ": " + notFoundException.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handlerException(NoResourceFoundException noResourceFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(noResourceFoundException.getMessage());
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<String> handlerException(DataValidationException dataValidationException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageException.DATA_VALIDATION_ERROR.getMessage() + ": " + dataValidationException.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handlerException(NullPointerException nullPointerException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(MessageException.DATA_VALIDATION_ERROR.getMessage() + ": " + nullPointerException.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handlerException(DuplicateKeyException duplicateKeyException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageException.DATA_VALIDATION_ERROR.getMessage() + ": " + duplicateKeyException.getMessage());
    }


}
