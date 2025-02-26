package co.edu.eci.ecoappetite.server.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "consumidores")

public class ConsumidorEntidad {
    //Clase que representa la coleccion de consumidores en MongoDB
    @Id
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String preferencias; //JSON o lista de categorías

}