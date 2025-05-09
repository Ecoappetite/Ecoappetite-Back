package co.edu.eci.ecoappetite.server.service.jwt;

import co.edu.eci.ecoappetite.server.exception.DataValidationException;

public interface ValidadorJwt {

    public Boolean validarToken(String token) throws DataValidationException;
    
}
