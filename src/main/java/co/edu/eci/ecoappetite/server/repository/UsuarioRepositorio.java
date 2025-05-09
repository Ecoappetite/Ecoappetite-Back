package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.dto.UsuarioDTO;
import co.edu.eci.ecoappetite.server.domain.entity.UsuarioEntidad;
import co.edu.eci.ecoappetite.server.domain.model.Usuario;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;

public interface UsuarioRepositorio {

    void registrarUsuario(Usuario usuario) throws EcoappetiteException;
    boolean existeUsuario(String correo);
    Usuario consultarUsuarioPorId(String correo) throws NotFoundException;
    
}
