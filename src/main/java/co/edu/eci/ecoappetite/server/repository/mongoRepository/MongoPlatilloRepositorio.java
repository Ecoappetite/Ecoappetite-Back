package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.entity.PlatilloEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.repository.PlatilloRepositorio;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoPlatilloRepositorio implements PlatilloRepositorio {

    private final MongoPlatilloInterface mongoPlatilloInterface;
    private final PlatilloMapper platilloMapper;


    @Override
    public Platillo agregarPlatillo(Platillo platillo) throws EcoappetiteException {
        PlatilloEntidad platilloEntidad = platilloMapper.toEntity(platillo);
        PlatilloEntidad platilloAgregado = mongoPlatilloInterface.save(platilloEntidad);
        return platilloMapper.toDomain(platilloAgregado);
    }

    @Override
    public List<Platillo> consultarTodosLosPlatillosos() {
        return mongoPlatilloInterface.findAll()
                .stream()
                .map(platilloMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Platillo consultarPlatilloPorId(String id) throws EcoappetiteException {
        PlatilloEntidad platilloEntidad = mongoPlatilloInterface.findById(id)
                .orElseThrow(() -> new NotFoundException("EL Platillo no ha sido encontrado"));

        return platilloMapper.toDomain(platilloEntidad);                     
    }

    @Override
    public List<Platillo> consultarPlatilloPorNombre(String nombre) throws EcoappetiteException {
        return mongoPlatilloInterface.findByNombre(nombre)
                .stream()
                .map(platilloMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Platillo modificarPlatillo(String id, Platillo platillo) throws EcoappetiteException {
        PlatilloEntidad platilloEntidad = mongoPlatilloInterface.findById(id)
                .orElseThrow(() -> new NotFoundException("El Platillo no ha sido encontrado"));
        
        platilloEntidad.setNombre(platillo.getNombre());
        platilloEntidad.setPrecioOriginal(platillo.getPrecioOriginal());
        platilloEntidad.setPrecioDescuento(platillo.getPrecioDescuento());
        platilloEntidad.setEstadoPlatillo(platillo.getEstadoPlatillo());
        platilloEntidad.setCantidadDisponible(platillo.getCantidadDisponible());
        platilloEntidad.setImagen(platillo.getImagen());
        platilloEntidad.setDescripcion(platillo.getDescripcion());

        PlatilloEntidad platilloModificado = mongoPlatilloInterface.save(platilloEntidad);
        return platilloMapper.toDomain(platilloModificado);
    }

    @Override
    public void eliminarPlatillo(String id) throws EcoappetiteException{
        mongoPlatilloInterface.deleteById(id);
    }

    @Override
    public void eliminarPlatilloPorNitRestaurante(String nit) throws EcoappetiteException {
        mongoPlatilloInterface.deleteByNitRestaurante(nit);
    }
    
}
