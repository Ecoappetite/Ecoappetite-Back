package co.edu.eci.ecoappetite.server.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.service.jwt.ValidadorJwt;

@Service
public class AlmacenTokenServicioImpl implements AlmacenTokenServicio {

    private final Map<String, String> tokenMap ;

    private final ValidadorJwt validadorJwt;
    
    public AlmacenTokenServicioImpl(ValidadorJwt validadorJwt) {
        this.tokenMap = new HashMap<>();
        this.validadorJwt = validadorJwt;
    }

    @Override
    public void almacenarToken(String identToken, String token) throws DataValidationException{
        if (!validadorJwt.validarToken(token).booleanValue()) {throw new DataValidationException("Token no válido");}

        tokenMap.put(identToken, token); 
    }

    @Override
    public Boolean verificacionToken(String identToken, String token) throws DataValidationException{
        
        return tokenMap.containsKey(identToken) && this.validadorJwt.validarToken(token) && tokenMap.get(identToken).equals(token);
    }

   
    
}
