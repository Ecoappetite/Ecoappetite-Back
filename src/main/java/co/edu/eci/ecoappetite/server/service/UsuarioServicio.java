package co.edu.eci.ecoappetite.server.service;

import co.edu.eci.ecoappetite.server.domain.dto.UsuarioDTO;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;

public interface UsuarioServicio {

    void registrarUsuario(UsuarioDTO usuarioDTO) throws EcoappetiteException;
    String login(UsuarioDTO usuarioDTO) throws NotFoundException, DataValidationException;
    void logout(UsuarioDTO usuarioDTO) throws NotFoundException;
    
}
