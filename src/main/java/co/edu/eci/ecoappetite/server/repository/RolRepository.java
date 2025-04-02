package co.edu.eci.ecoappetite.server.repository;

import co.edu.eci.ecoappetite.server.domain.model.Rol;
import co.edu.eci.ecoappetite.server.domain.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RolRepository extends MongoRepository<Rol, Long> {
    Optional<Rol> findByNombre(Role nombre);
}