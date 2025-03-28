package co.edu.eci.ecoappetite.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.repository.PlatilloRepositorio;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PlatilloServicioImpl implements PlatilloServicio{

    private final PlatilloRepositorio platilloRepositorio;
    private final PlatilloMapper platilloMapper;

    @Override
    public PlatilloDTO agregarPlatillo(PlatilloDTO platilloDTO) throws EcoappetiteException {
        Platillo platillo = platilloMapper.toDomain(platilloDTO);
        PlatilloDataValidator.validar(platillo);
        Platillo nuevoPlatillo = platilloRepositorio.agregarPlatillo(platillo);
        return platilloMapper.toDTO(nuevoPlatillo);
    }

    @Override
    public List<PlatilloDTO> consultarTodosLosPlatillos() {
        return platilloRepositorio.consultarTodosLosPlatillosos()
                .stream()
                .map(platilloMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatilloDTO consultarPlatilloPorId(String id) throws EcoappetiteException {
        Platillo platillo = platilloRepositorio.consultarPlatilloPorId(id);
        return platilloMapper.toDTO(platillo);
    }

    @Override
    public List<PlatilloDTO> consultarPlatilloPorNombre(String nombre) throws EcoappetiteException {
        return platilloRepositorio.consultarPlatilloPorNombre(nombre)
                .stream()
                .map(platilloMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatilloDTO modificarPlatillo(String id, PlatilloDTO platilloDTO) throws EcoappetiteException {
        Platillo platillo = platilloMapper.toDomain(platilloDTO);
        PlatilloDataValidator.validar(platillo);
        Platillo platilloModificado = platilloRepositorio.modificarPlatillo(id, platillo);
        return platilloMapper.toDTO(platilloModificado); 
    }

    @Override
    public void eliminarPlatillo(String id) throws EcoappetiteException{
        platilloRepositorio.eliminarPlatillo(id);
    }
    
}
