package co.edu.eci.ecoappetite.server.service.motor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.ConsumidorDTO;
import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.dto.recomendacion.RestauranteRecomendacionDTO;
import co.edu.eci.ecoappetite.server.domain.model.Platillo;
import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.domain.model.Restaurante;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.RegistroHistorialRepositorio;
import co.edu.eci.ecoappetite.server.service.ConsumidorServicio;
import co.edu.eci.ecoappetite.server.service.PlatilloServicio;
import co.edu.eci.ecoappetite.server.service.RestauranteServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MotorRecomendacion {

    private final Mensajero mensajero;
    private final RegistroHistorialRepositorio registroHistorialRepositorio;
    private final ConsumidorServicio consumidorServicio;
    private final RestauranteServicio restauranteServicio;
    private final PlatilloServicio platilloServicio;
    private final RestauranteMapper restauranteMapper;
    private final PlatilloMapper platilloMapper;

    @Value("${ecoappetite.ai.retries}")
    private Integer intentosMaximos;


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
        int intentos = 1;
        boolean huboRespuesta = false;
        String respuesta = "";
        while (!huboRespuesta && intentos<= intentosMaximos) {
            try {
                respuesta = mensajero.pedirRecomendaciones(consumidor, registroCliente, listRecomendacionRestaurante);
                huboRespuesta = !respuesta.isEmpty();
                
            } catch (EcoappetiteException e) {
                log.info("[REINTENTO DE MENSAJES] intento {}, error {}",intentos, e.getMessage());
                
            } finally{
                intentos++;
            } 
        }

        if (intentos==intentosMaximos && !huboRespuesta) {
            throw new EcoappetiteException("Limites de intentos alcanzados sin respuesta");
        }
        return respuesta;
    }

    public void inyectarRecomendacion(String idConsumidor, String restauranteId, String platilloId) throws EcoappetiteException {
        RestauranteDTO restauranteDTO = restauranteServicio.consultarRestaurantePorId(restauranteId);
        PlatilloDTO platilloDTO = platilloServicio.consultarPlatilloPorId(platilloId);

        Restaurante restaurante = restauranteMapper.toDomain(restauranteDTO);
        Platillo platillo = platilloMapper.toDomain(platilloDTO);

        restaurante.setPlatillos(null);
        RegistroHistorial registroHistorial = new RegistroHistorial(null, idConsumidor, restaurante, platillo, LocalDateTime.now());
        registroHistorialRepositorio.guardarRegistroHistorial(registroHistorial);
    }

    public List<RegistroHistorial> consultarTodasLasRecomendaciones() {
        return registroHistorialRepositorio.consultarTodasLasRecomendaciones();
        
    }
}
