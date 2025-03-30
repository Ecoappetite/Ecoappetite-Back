package co.edu.eci.ecoappetite.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.DuplicationErrorException;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestauranteServicioImpl implements RestauranteServicio {

    private final RestauranteRepositorio restauranteRepositorio;
    private final RestauranteMapper restauranteMapper;
    private final PlatilloMapper platilloMapper;
    private final PlatilloServicio platilloServicio;

    @Override
    public RestauranteDTO registrarRestaurante(RestauranteDTO restauranteDTO) throws EcoappetiteException {
        Restaurante restaurante = restauranteMapper.toDomain(restauranteDTO);
        RestauranteDataValidator.validar(restaurante);
        Restaurante nuevoRestaurante = restauranteRepositorio.registrarRestaurante(restaurante);
        return restauranteMapper.toDTO(nuevoRestaurante);
    }

    @Override
    public List<RestauranteDTO> consultarTodosLosRestaurantes() {
        return restauranteRepositorio.consultarTodosLosRestaurantes()
                .stream()
                .map(restauranteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RestauranteDTO consultarRestaurantePorId(String id) throws EcoappetiteException {
        Restaurante restaurante = restauranteRepositorio.consultarRestaurantePorId(id);
        return restauranteMapper.toDTO(restaurante);       
    }

    @Override
    public RestauranteDTO consultarRestaurantePorNombre(String nombre) throws EcoappetiteException {
        Restaurante restaurante = restauranteRepositorio.consultarRestaurantePorNombre(nombre);
        return restauranteMapper.toDTO(restaurante);        
    }

    @Override
    public RestauranteDTO modificarRestaurante(String id, RestauranteDTO restauranteDTO) throws EcoappetiteException {
        Restaurante restaurante = restauranteMapper.toDomain(restauranteDTO);
        RestauranteDataValidator.validar(restaurante);
        Restaurante restauranteModificado = restauranteRepositorio.modificarRestaurante(id, restaurante);
        return restauranteMapper.toDTO(restauranteModificado);
    }

    @Override
    public void eliminarRestaurante(String nit) throws EcoappetiteException {
        platilloServicio.eliminarPlatilloPorNitRestaurante(nit);
        restauranteRepositorio.eliminarRestaurante(nit);        
    }

    @Override
    public void agregarPlatilloRestaurante(String nombre, PlatilloDTO platilloDTO) throws EcoappetiteException {
        Platillo platillo = platilloMapper.toDomain(platilloDTO);
        if(restauranteRepositorio.existePlatillo(nombre, platillo)) throw new DuplicationErrorException("El platillo " + platillo.getNombre() + " ya fue agregado al restaurante");
        PlatilloDTO platilloDTOGuardado = platilloServicio.agregarPlatillo(platilloDTO);
        restauranteRepositorio.agregarPlatilloRestaurante(nombre,platilloMapper.toDomain(platilloDTOGuardado));
    }

    @Override
    public void eliminarPlatilloRestaurante(String nit, String idPlatillo) throws EcoappetiteException {
        platilloServicio.eliminarPlatillo(idPlatillo);        
        restauranteRepositorio.eliminarPlatilloRestaurante(nit, idPlatillo);
    
    }

    @Override
    public void modificarPlatilloRestaurante(String nit, String idPlatillo, PlatilloDTO platilloDTO) throws EcoappetiteException{
        Platillo platillo = platilloMapper.toDomain(platilloDTO);
        if(restauranteRepositorio.existePlatillo(nit, platillo)) throw new DuplicationErrorException("El platillo " + platillo.getNombre() + " ya fue agregado al restaurante");
        PlatilloDTO platilloDTOModificado = platilloServicio.modificarPlatillo(idPlatillo,platilloDTO);
        restauranteRepositorio.modificarPlatilloRestaurante(nit, idPlatillo, platilloMapper.toDomain(platilloDTOModificado));
    }
    
}
