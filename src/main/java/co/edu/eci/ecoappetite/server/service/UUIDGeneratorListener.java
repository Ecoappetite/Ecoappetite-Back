package co.edu.eci.ecoappetite.server.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import co.edu.eci.ecoappetite.server.domain.entity.PlatilloEntidad;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

@Service
public class UUIDGeneratorListener extends AbstractMongoEventListener<PlatilloEntidad>{
    
    @Override
    public void onBeforeConvert(BeforeConvertEvent<PlatilloEntidad> evento){
        PlatilloEntidad platilloEntidad = evento.getSource();
        if(platilloEntidad.getId() == null){
            platilloEntidad.setId(UUID.randomUUID().toString());
        }
    }
    
}
