package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestauranteDataValidator {
    
    public static void validar(Restaurante restaurante) throws DataValidationException{
        RestauranteDataValidator validador = new RestauranteDataValidator(restaurante);
        validador.datosRestauranteSinCadenasVacias();        
    }

    private final Restaurante restaurante;

    public RestauranteDataValidator datosRestauranteSinCadenasVacias() throws DataValidationException{
        if(restaurante.getNombre().equals("") || restaurante.getDireccion().equals("") || 
            restaurante.getTelefono().equals("") || restaurante.getWhatsapp().equals("") || 
            restaurante.getImagen().equals("") || restaurante.getDescripcion().equals("")) throw new DataValidationException("Ninguno de los datos del restaurante puede ser una cadena vacia");
        return this;
    }  


    
}
