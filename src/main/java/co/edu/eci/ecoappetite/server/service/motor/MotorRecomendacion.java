package co.edu.eci.ecoappetite.server.service.motor;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.dto.recomendacion.RestauranteRecomendacionDTO;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RegistroHistorialRepositorio;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import co.edu.eci.ecoappetite.server.service.RestauranteServicio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MotorRecomendacion {

    private final Mensajero mensajero;
    private final RegistroHistorialRepositorio registroHistorialRepositorio;
    private final ConsumidorServicio consumidorServicio;
    private final RestauranteServicio restauranteServicio;
    private final RestauranteMapper restauranteMapper;

    @Value("${ecoappetite.ai.retries}")
    private Integer INTENTOS;


    public String mensajeDevueltoPorIa (String idConsumidor) throws EcoappetiteException{
        /*llamar historial */
        ConsumidorDTO consumidor = consumidorServicio.consultarConsumidorPorId(idConsumidor);
        var registroCliente = registroHistorialRepositorio.consultaRegistroHistorial(idConsumidor);
        var listaRestaurantes = restauranteServicio.consultarTodosLosRestaurantes();
        var listRecomendacionRestaurante = listaRestaurantes.parallelStream()
                .map(restauranteMapper::toRecomendacion)
                .toList();
        return this.reintentos(consumidor, registroCliente, listRecomendacionRestaurante);

    }

    private String reintentos(ConsumidorDTO consumidor, List<RegistroHistorial> registroCliente, List<RestauranteRecomendacionDTO> listRecomendacionRestaurante) throws EcoappetiteException{
        int intentos = INTENTOS;
        boolean huboRespuesta = false;
        String respuesta = "";
        while (!huboRespuesta && intentos-- > 0) {
            try {
                respuesta = mensajero.pedirRecomendaciones(consumidor, registroCliente, listRecomendacionRestaurante);
                huboRespuesta = !respuesta.isEmpty();
                
            } catch (EcoappetiteException e) {
                
            }
            
        }

        if (intentos==0 && !huboRespuesta) {
            throw new EcoappetiteException("Limites de intentos alcanzados sin respuesta");
            
        }
        return respuesta;
    }
}
