package co.edu.eci.ecoappetite.server.service;

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
        System.out.println(platilloDTO);
        Platillo platillo = platilloMapper.toDomain(platilloDTO);
        System.out.println(platillo);
        Platillo nuevoPlatillo = platilloRepositorio.agregarPlatillo(platillo);
        return platilloMapper.toDTO(nuevoPlatillo);
    }
    
}
