package co.edu.eci.ecoappetite.server.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "historial")
public class RegistroHistorialEntidad {
    
    @Id
    private String idRegistroHistorial;
    private String idConsumidor;

    @Field
    @JsonIgnoreProperties({"platillos"})
    private RestauranteEntidad restaurante;

    @Field
    private PlatilloEntidad platillo;

    private LocalDateTime fecha;
    

    
}
