package co.edu.eci.ecoappetite.server.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.UsuarioDTO;
import co.edu.eci.ecoappetite.server.domain.model.Usuario;
import co.edu.eci.ecoappetite.server.exception.DataValidationException;
import co.edu.eci.ecoappetite.server.exception.DuplicationErrorException;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.exception.LoginException;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
import co.edu.eci.ecoappetite.server.repository.UsuarioRepositorio;
import co.edu.eci.ecoappetite.server.service.jwt.JwtServicio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServicioImpl implements UsuarioServicio {
    
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepositorio usuarioRepositorio;
    private final JwtServicio jwtServicio;
    private final AlmacenTokenServicio almacenTokenServicio;

    @Override
    public void registrarUsuario(UsuarioDTO usuarioDTO) throws EcoappetiteException {
        String correo = usuarioDTO.getCorreo();
        String contrasenaEncriptada = passwordEncoder.encode(usuarioDTO.getContrasena());
        if(usuarioRepositorio.existeUsuario(correo)) throw new DuplicationErrorException("El usuario: " + correo + " ya fue registrado");
        Usuario usuario = Usuario.builder()
                    .correo(correo)
                    .contrasena(contrasenaEncriptada)
                    .rol(usuarioDTO.getRol())
                    .idEntidadAlmacenada(usuarioDTO.getIdEntidadAlmacenada())
                    .build();

        usuarioRepositorio.registrarUsuario(usuario);
    }

    @Override
    public String login(UsuarioDTO usuarioDTO) throws NotFoundException, DataValidationException {
        Usuario usuario = usuarioRepositorio.consultarUsuarioPorId(usuarioDTO.getCorreo());
        if(!passwordEncoder.matches(usuarioDTO.getContrasena(), usuario.getContrasena())) throw new LoginException("Usuario o contrasena no valido");
        String token = jwtServicio.crearToken(usuario);
        almacenTokenServicio.almacenarToken(usuario.getCorreo(), token);
        return token;
    }
    
}
