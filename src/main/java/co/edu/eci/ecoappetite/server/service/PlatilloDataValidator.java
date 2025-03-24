package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.model.EstadoPlatillo;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlatilloDataValidator {

    public static void validar(Platillo platillo) throws DataValidationException{
        PlatilloDataValidator validador = new PlatilloDataValidator(platillo);
        validador.precioOriginalMayorPrecioDescuento()
                .estadoPlatilloAgotado()
                .datosPlatilloSinCadenasVacias()
                .preciosMayoresACero()
                .cantidadDisponibleMayorACero();        
    }

    private final Platillo platillo;

    public PlatilloDataValidator precioOriginalMayorPrecioDescuento() throws DataValidationException{
        if(platillo.getPrecioDescuento() > platillo.getPrecioOriginal()) throw new DataValidationException("El precio de descuento no puede ser mayor al precio original");
        return this;
    }
    
    public PlatilloDataValidator estadoPlatilloAgotado() throws DataValidationException{
        if(platillo.getEstadoPlatillo().equals(EstadoPlatillo.AGOTADO) && platillo.getCantidadDisponible() != 0) throw new DataValidationException("El estado del platillo no puede ser AGOTADO con cantidad disponible");
        return this;
    }

    public PlatilloDataValidator datosPlatilloSinCadenasVacias() throws DataValidationException{
        if(platillo.getNombre().equals("") || platillo.getImagen().equals("") || 
            platillo.getDescripcion().equals("") || platillo.getNitRestaurante().equals("")) throw new DataValidationException("Ninguno de los datos del platillo puede ser una cadena vacia");
        return this;
    }

    public PlatilloDataValidator preciosMayoresACero() throws DataValidationException{
        if(platillo.getPrecioOriginal() <= 0 || platillo.getPrecioDescuento() <= 0) throw new DataValidationException("Ninguno de los precios del platillo puede ser cero");
        return this;
    }

    public PlatilloDataValidator cantidadDisponibleMayorACero() throws DataValidationException{
        if(platillo.getCantidadDisponible() < 0) throw new DataValidationException("La cantidad del platillo no puede ser menor a cero");
        return this;
    }
}
