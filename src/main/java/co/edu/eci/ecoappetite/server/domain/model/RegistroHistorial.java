package co.edu.eci.ecoappetite.server.domain.model;

import java.time.LocalDateTime;

import com.mongodb.lang.NonNull;


public class RegistroHistorial {

    private String idRegistroHistorial;

    @NonNull
    private String idConsumidor;
    
    
    private Restaurante restaurante;   
    private Platillo platillo;

    @NonNull
    private LocalDateTime fecha;


    
}
