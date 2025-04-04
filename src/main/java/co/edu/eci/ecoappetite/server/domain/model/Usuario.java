package co.edu.eci.ecoappetite.server.domain.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data  // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Document(collection = "usuarios") // Indica que esta clase representa una colección en MongoDB
public class Usuario {
    @Id
    private String id; // MongoDB usa ID tipo String en lugar de Long

    private String nombre;
}
