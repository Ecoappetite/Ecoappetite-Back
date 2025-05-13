package co.edu.eci.ecoappetite.server.repository;

import java.util.List;

import co.edu.eci.ecoappetite.server.domain.model.RegistroHistorial;
import co.edu.eci.ecoappetite.server.exception.EcoappetiteException;

public interface RegistroHistorialRepositorio {

  RegistroHistorial guardarRegistroHistorial(RegistroHistorial registroHistorial) throws EcoappetiteException;
  List<RegistroHistorial> consultaRegistroHistorial(String idConsumidor);
  List<RegistroHistorial> consultarTodasLasRecomendaciones();
    
}
