package co.edu.eci.ecoappetite.server.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.repository.PlatilloRepositorio;


@Service
public class PlatilloServicioImpl implements PlatilloServicio{

    private final PlatilloRepositorio platilloRepositorio;
    private final PlatilloMapper platilloMapper;

    @Autowired
    public PlatilloServicioImpl(PlatilloRepositorio platilloRepositorio, PlatilloMapper platilloMapper){
        this.platilloRepositorio = platilloRepositorio;
        this.platilloMapper = platilloMapper;
    }

    @Override
    public PlatilloDTO agregarPlatillo(PlatilloDTO platilloDTO) throws EcoappetiteException {
        Platillo platillo = platilloMapper.toDomain(platilloDTO);
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
    
}
