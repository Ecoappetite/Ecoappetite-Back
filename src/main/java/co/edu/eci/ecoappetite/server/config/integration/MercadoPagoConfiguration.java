package co.edu.eci.ecoappetite.server.config.integration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MercadoPagoConfiguration {

    @Bean
    public PreferenceClient preferenceClient(@Value("${mercadopago.access.token}") String accessToken) {
        MercadoPagoConfig.setAccessToken(accessToken);
        return new PreferenceClient();
    }
}