package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.stereotype.Repository;

import co.edu.eci.ecoappetite.server.domain.dto.UsuarioDTO;
import co.edu.eci.ecoappetite.server.domain.entity.UsuarioEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Usuario;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.mapper.UsuarioMapper;
import co.edu.eci.ecoappetite.server.repository.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MongoUsuarioRepositorio implements UsuarioRepositorio {

    private final MongoUsuarioInterface mongoUsuarioInterface;
    private final UsuarioMapper usuarioMapper;

    @Override
    public void registrarUsuario(Usuario usuario) throws EcoappetiteException {
        UsuarioEntidad usuarioEntidad = usuarioMapper.toEntity(usuario);        
        mongoUsuarioInterface.save(usuarioEntidad);
    }

    @Override
    public boolean existeUsuario(String correo) {
        return mongoUsuarioInterface.existsById(correo);
    }

    @Override
    public Usuario consultarUsuarioPorId(String correo) throws NotFoundException {
        UsuarioEntidad usuarioEntidad = mongoUsuarioInterface.findById(correo)
                    .orElseThrow(() -> new NotFoundException("El usuario no fue encontrado"));
        
        return usuarioMapper.toDomain(usuarioEntidad);
    }
    
}
