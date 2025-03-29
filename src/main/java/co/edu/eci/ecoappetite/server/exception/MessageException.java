package co.edu.eci.ecoappetite.server.exception;

import lombok.Getter;
import lombok.NonNull;

@Getter
public enum MessageException {

    GENERAL_ERROR("Internal server error"),
    NOT_FOUND_ERROR("Recurso no encontrado"),
    DATA_VALIDATION_ERROR("Dato no valido"),
    DATA_DUPLICATION_ERROR("Dato duplicado");

    private String message;

    private MessageException(@NonNull String message){
        this.message = message;
    }
    
}
