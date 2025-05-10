package co.edu.eci.ecoappetite.server.repository.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.eci.ecoappetite.server.domain.entity.UsuarioEntidad;

public interface MongoUsuarioInterface extends MongoRepository<UsuarioEntidad, String>{
    
}
