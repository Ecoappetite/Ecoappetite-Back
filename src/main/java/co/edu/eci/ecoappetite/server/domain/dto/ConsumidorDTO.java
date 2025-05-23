package co.edu.eci.ecoappetite.server.domain.dto;



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

}
