package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;

public interface AlmacenTokenServicio {

    public void almacenarToken (String identToken, String token) throws DataValidationException;

    public Boolean verificacionToken(String token) throws DataValidationException;

    public Rol obtenerRol(String token) throws DataValidationException;

    public String obtenerSubject (String token) throws DataValidationException;

    
}
