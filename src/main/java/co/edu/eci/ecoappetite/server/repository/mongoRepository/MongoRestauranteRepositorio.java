package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;

@Repository
public class MongoRestauranteRepositorio implements RestauranteRepositorio{

    private MongoRestauranteInterface mongoRestauranteInterface;

    @Autowired
    public MongoRestauranteRepositorio(MongoRestauranteInterface mongoRestauranteInterface){
        this.mongoRestauranteInterface = mongoRestauranteInterface;

    }

    @Override
    public Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = new RestauranteEntidad();
        restauranteEntidad.setNit(restaurante.getNit());
        restauranteEntidad.setNombre(restaurante.getNombre());
        restauranteEntidad.setDireccion(restaurante.getDireccion());
        restauranteEntidad.setTelefono(restaurante.getTelefono());
        restauranteEntidad.setWhatsapp(restaurante.getWhatsapp());
        restauranteEntidad.setCategoria(restaurante.getCategoria());
        restauranteEntidad.setImagen(restaurante.getImagen());
        restauranteEntidad.setDescripcion(restaurante.getDescripcion());
        
        RestauranteEntidad restauranteResultado = mongoRestauranteInterface.save(restauranteEntidad);

        return new Restaurante(
            restauranteResultado.getNit(),
            restauranteResultado.getNombre(),
            restauranteResultado.getDireccion(),
            restauranteResultado.getTelefono(),
            restauranteResultado.getWhatsapp(),
            restauranteResultado.getCategoria(),
            restauranteResultado.getImagen(),
            restauranteResultado.getDescripcion()
        );
    }
    
}
