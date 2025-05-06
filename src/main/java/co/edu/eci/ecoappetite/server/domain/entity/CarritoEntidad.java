package co.edu.eci.ecoappetite.server.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "carritos")
public class CarritoEntidad {
    @Id
    private String consumidorId;
    private List<String> productos;
    private double total;
}
