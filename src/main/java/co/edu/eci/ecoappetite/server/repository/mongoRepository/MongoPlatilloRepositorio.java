package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.entity.PlatilloEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.repository.PlatilloRepositorio;

@Repository
public class MongoPlatilloRepositorio implements PlatilloRepositorio {

    private MongoPlatilloInterface mongoPlatilloInterface;
    private PlatilloMapper platilloMapper;

    @Autowired
    public MongoPlatilloRepositorio(MongoPlatilloInterface mongoPlatilloInterface, PlatilloMapper platilloMapper){
        this.mongoPlatilloInterface = mongoPlatilloInterface;
        this.platilloMapper = platilloMapper;

    }

    @Override
    public Platillo agregarPlatillo(Platillo platillo) throws EcoappetiteException {
        PlatilloEntidad platilloEntidad = platilloMapper.toEntity(platillo);
        PlatilloEntidad platilloAgregado = mongoPlatilloInterface.save(platilloEntidad);
        return platilloMapper.toDomain(platilloAgregado);
    }
    
}
