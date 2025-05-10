package co.edu.eci.ecoappetite.server.exception;

public class LoginException extends DataValidationException {

    public LoginException(String mensaje){
        super(mensaje);
    }
    
}
