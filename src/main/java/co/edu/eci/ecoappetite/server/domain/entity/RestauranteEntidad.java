package co.edu.eci.ecoappetite.server.domain.entity;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.eci.ecoappetite.server.domain.model.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restaurantes")
public class RestauranteEntidad {

    @Id
    private String nit;
    
    @Indexed(unique = true)
    private String nombre;
    
    private String direccion;
    private String telefono;
    private String whatsapp;
    private Categoria categoria;
    private String imagen;
    private String descripcion;
    private Collection<PlatilloEntidad> platillos;
    
}
