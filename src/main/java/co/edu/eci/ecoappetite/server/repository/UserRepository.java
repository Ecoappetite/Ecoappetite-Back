package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositorio para la entidad User.
 * Proporciona métodos para acceder a los usuarios en la base de datos.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario (email).
     * 
     * @param email Correo electrónico del usuario.
     * @return Un Optional que contiene el usuario si existe.
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica si un usuario con el email proporcionado ya existe.
     * 
     * @param email Correo electrónico del usuario.
     * @return true si el usuario existe, false en caso contrario.
     */
    boolean existsByEmail(String email);
}
