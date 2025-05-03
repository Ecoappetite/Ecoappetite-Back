package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import java.util.List;


import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.entity.RegistroHistorialEntidad;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.RegistroHistorialMapper;
import co.edu.eci.ecoappetite.server.repository.RegistroHistorialRepositorio;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoRegistroHistorialRepositorio implements RegistroHistorialRepositorio {


    private final MongoRegistroHistorialInterface mongoRegistroHistorialInterface;
    private final RegistroHistorialMapper registroHistorialMapper;

    @Override
    public RegistroHistorial guardarRegistroHistorial(RegistroHistorial registroHistorial) throws EcoappetiteException {
        RegistroHistorialEntidad registroHistorialEntidad = registroHistorialMapper.toEntity(registroHistorial);
        RegistroHistorialEntidad registroHistorialGuardado = mongoRegistroHistorialInterface.save(registroHistorialEntidad);
        return registroHistorialMapper.toDomain(registroHistorialGuardado);
    }

    @Override
    public List<RegistroHistorial> consultaRegistroHistorial(String idConsumidor) {
        return mongoRegistroHistorialInterface.findByIdConsumidor(idConsumidor)
            .stream()
            .map(registroHistorialMapper::toDomain)
            .toList();
 
    }
    
}
