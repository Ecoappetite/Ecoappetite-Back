package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.entity.PlatilloEntidad;
import co.edu.eci.ecoappetite.server.domain.entity.RestauranteEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoRestauranteRepositorio implements RestauranteRepositorio{

    private final MongoRestauranteInterface mongoRestauranteInterface;
    private final RestauranteMapper restauranteMapper;
    private final PlatilloMapper platilloMapper;

    @Override
    public Restaurante registrarRestaurante(Restaurante restaurante) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = restauranteMapper.toEntity(restaurante);
        RestauranteEntidad restauranteRegistrado = mongoRestauranteInterface.save(restauranteEntidad);
        return restauranteMapper.toDomain(restauranteRegistrado);
    }

    @Override
    public List<Restaurante> consultarTodosLosRestaurantes() {
        return mongoRestauranteInterface.findAll()
                .stream()
                .map(restauranteMapper::toDomain)
                .toList();
      
    }

    @Override
    public Restaurante consultarRestaurantePorId(String id) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findById(id)
                .orElseThrow(() -> new NotFoundException("Este restaurante no fue encontrado"));
        
        return restauranteMapper.toDomain(restauranteEntidad);
    }

    @Override
    public Restaurante consultarRestaurantePorNombre(String nombre) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findByNombre(nombre)
                .orElseThrow(() -> new NotFoundException("Este restaurante no fue encontrado"));
        
        return restauranteMapper.toDomain(restauranteEntidad);
    }

    @Override
    public Restaurante modificarRestaurante(String id, Restaurante restaurante) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findById(id)
                .orElseThrow(() -> new NotFoundException("Este restaurante no fue encontrado"));

        restauranteEntidad.setDireccion(restaurante.getDireccion());
        restauranteEntidad.setTelefono(restaurante.getTelefono());
        restauranteEntidad.setWhatsapp(restaurante.getWhatsapp());
        restauranteEntidad.setImagen(restaurante.getImagen());
        restauranteEntidad.setDescripcion(restaurante.getDescripcion());

        RestauranteEntidad restauranteModificado = mongoRestauranteInterface.save(restauranteEntidad);

        return restauranteMapper.toDomain(restauranteModificado);
    }

    @Override
    public void eliminarRestaurante(String id) throws EcoappetiteException {
        mongoRestauranteInterface.deleteById(id);
    }

    @Override
    public Restaurante agregarPlatilloRestaurante(String nombre, Platillo platillo) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findByNombre(nombre)
                .orElseThrow(() -> new NotFoundException("Este restaurante no fue encontrado"));
        PlatilloEntidad platilloEntidad = platilloMapper.toEntity(platillo);
        restauranteEntidad.getPlatillos().add(platilloEntidad);
        RestauranteEntidad nuevoRestaurante = mongoRestauranteInterface.save(restauranteEntidad);
        return restauranteMapper.toDomain(nuevoRestaurante);
    }

    @Override
    public void eliminarPlatilloRestaurante(String nit, String idPlatillo) throws EcoappetiteException {
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findById(nit)
                .orElseThrow(() -> new NotFoundException("Este restaurante no fue encontrado"));
        restauranteEntidad.getPlatillos().removeIf(platillo -> platillo.getId().equals(idPlatillo));
        mongoRestauranteInterface.save(restauranteEntidad);
    }

    @Override
    public Restaurante modificarPlatilloRestaurante(String nit, String idPlatillo, Platillo platillo) throws EcoappetiteException{
        RestauranteEntidad restauranteEntidad = mongoRestauranteInterface.findById(nit)
                .orElseThrow(() -> new NotFoundException("Este restaurante no fue encontrado"));

        List<PlatilloEntidad> platillos = new ArrayList<>(restauranteEntidad.getPlatillos());

        boolean modificado = false;
        for (int i = 0; i < platillos.size(); i++) {
            if (platillos.get(i).getId().equals(idPlatillo)) {
                PlatilloEntidad platilloEntidadActualizado = platilloMapper.toEntity(platillo);
                platilloEntidadActualizado.setId(idPlatillo);
                platillos.set(i, platilloEntidadActualizado);
                modificado = true;
                break;
            }
        }
            
        if (!modificado) throw new NotFoundException("El platillo con ID " + idPlatillo + " no fue encontrado en el restaurante");
            
        restauranteEntidad.setPlatillos(platillos);
        RestauranteEntidad restauranteActualizado = mongoRestauranteInterface.save(restauranteEntidad);
        return restauranteMapper.toDomain(restauranteActualizado);

    }

    @Override
    public boolean existePlatillo(String nombre, Platillo platillo) {
        return mongoRestauranteInterface.existsByNombreAndPlatillosNombre(nombre, platillo.getNombre());
    }

}
