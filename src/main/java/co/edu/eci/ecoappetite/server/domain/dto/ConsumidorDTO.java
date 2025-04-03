package co.edu.eci.ecoappetite.server.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConsumidorDTO {
    //Clase que recibe y devuelve los datos

    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String preferencias;

    private List<String> restaurantesFavoritos;
    private List<String> platillosFavoritos;
}
