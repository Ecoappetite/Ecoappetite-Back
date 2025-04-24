package co.edu.eci.ecoappetite.server.service.motor;

import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.exception.NotFoundException;
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


    public String mensajeDevueltoPorIa (String idConsumidor) throws NotFoundException{
        /*llamar historial */
        ConsumidorDTO consumidor = consumidorServicio.consultarConsumidorPorId(idConsumidor);
        List<RegistroHistorial> registroCliente = registroHistorialRepositorio.consultaRegistroHistorial(idConsumidor);
        List<RestauranteDTO> listaRestaurantes = restauranteServicio.consultarTodosLosRestaurantes();
        return mensajero.pedirRecomendaciones(consumidor, registroCliente, listaRestaurantes);

    }
}
