package co.edu.eci.ecoappetite.server.config.data;


import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "ecoappetite.ai.openai")
public class ClienteIAProperties{

   
    private String apiKey;
    private String model;
    private String url;
    private String bearer;

    @PostConstruct
    public void postConstructor(){
        this.setBearer("Bearer " + apiKey);
    }
    
    
}
