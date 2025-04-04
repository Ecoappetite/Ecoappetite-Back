package co.edu.eci.ecoappetite.server.service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.dto.PlatilloDTO;
import co.edu.eci.ecoappetite.server.domain.dto.RestauranteDTO;
import co.edu.eci.ecoappetite.server.domain.model.Consumidor;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;
import co.edu.eci.ecoappetite.server.mapper.PlatilloMapper;
import co.edu.eci.ecoappetite.server.mapper.RestauranteMapper;
import co.edu.eci.ecoappetite.server.repository.ConsumidorRepositorio;
import co.edu.eci.ecoappetite.server.repository.PlatilloRepositorio;
import co.edu.eci.ecoappetite.server.repository.RestauranteRepositorio;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecomendacionServicioImpl implements RecomendacionServicio {

    private final ConsumidorRepositorio consumidorRepositorio;
    private final RestauranteRepositorio restauranteRepositorio;
    private final PlatilloRepositorio platilloRepositorio;
    private final RestauranteMapper restauranteMapper;
    private final PlatilloMapper platilloMapper;

    @Override
    public List<RestauranteDTO> recomendarRestaurante(String id) throws EcoappetiteException {
        Consumidor consumidor = consumidorRepositorio.consultarConsumidorPorId(id);
        List<String> favoritos = consumidor.getRestaurantesFavoritos(); 

        Set<String> categoriasFavoritas = favoritos.stream()
            .map(nit -> {
                try {
                    return restauranteRepositorio.consultarRestaurantePorId(nit).getCategoria().name();
                } catch (EcoappetiteException e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        return restauranteRepositorio.consultarTodosLosRestaurantes()
            .stream()
            .filter(r -> categoriasFavoritas.contains(r.getCategoria().name()))
            .map(restauranteMapper::toDTO)
            .collect(Collectors.toList());     
    }

    @Override
    public List<PlatilloDTO> recomendarPlatillo(String id) throws EcoappetiteException {

        List<RestauranteDTO> restaurantesRecomendados = recomendarRestaurante(id);
        List<String> nits = restaurantesRecomendados.stream().map(RestauranteDTO::getNit).toList();

        return platilloRepositorio.consultarTodosLosPlatillosos()
            .stream()
            .filter(p -> nits.contains(p.getNitRestaurante()))
            .map(platilloMapper::toDTO)
            .collect(Collectors.toList());
    }

    
    
}
