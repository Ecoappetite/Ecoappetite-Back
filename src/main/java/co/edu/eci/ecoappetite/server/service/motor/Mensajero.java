package co.edu.eci.ecoappetite.server.service.motor;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.recomendacion.RestauranteRecomendacionDTO;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class Mensajero {

    private final ClienteIa clienteIa;

    public String pedirRecomendaciones(ConsumidorDTO consumidorDTO, List<RegistroHistorial> historial, List<RestauranteRecomendacionDTO> restaurante) throws EcoappetiteException{

        String prompt = this.construirPrompt(consumidorDTO, historial,restaurante);
        String respuesta = clienteIa.call(prompt);
        return this.parsearRespuesta(respuesta);
      
    }

    private String construirPrompt(ConsumidorDTO consumidorDTO, List<RegistroHistorial> historial, List<RestauranteRecomendacionDTO> restaurante){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Eres un catador experto en comida. Recomiéndale al consumidor, según su historial previo de compras, restaurantes de la siguiente lista que te entrego").append("\n")
                .append("consumidor: " + consumidorDTO).append("\n")
                .append("historial previo de compras: " + historial).append("\n")
                .append("lista de restaurantes: " + restaurante).append("\n")
                .append("entrega la información en un formato JSON con la lista de los restaurantes que le recomiendas al consumidor estas recomendaciones solo deben ser de la lista de restaurantes que te pase.");

        return stringBuilder.toString();
    }

    private String parsearRespuesta(String respuestaIA){

        JSONObject respuestaJSON = new JSONObject(respuestaIA);
        String contenido = respuestaJSON
            .getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content");

        return contenido;

    }


}
    
    
    

