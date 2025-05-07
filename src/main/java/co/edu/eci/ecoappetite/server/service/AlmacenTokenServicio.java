package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.exception.DataValidationException;

public interface AlmacenTokenServicio {

    public void almacenarToken (String identToken, String token) throws DataValidationException;

    public Boolean verificacionToken(String identToken, String token) throws DataValidationException;
    
}
