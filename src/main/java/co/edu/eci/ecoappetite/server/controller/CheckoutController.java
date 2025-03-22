package co.edu.eci.ecoappetite.server.controller;

import co.edu.eci.ecoappetite.server.ConfigLoader;
import co.edu.eci.ecoappetite.server.domain.dto.CheckoutResponse;
import co.edu.eci.ecoappetite.server.domain.dto.OrderItem;
import co.edu.eci.ecoappetite.server.domain.dto.OrderRequest;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final PreferenceClient preferenceClient;

    public CheckoutController() {
        // Inicializar Mercado Pago con el Access Token
        MercadoPagoConfig.setAccessToken(ConfigLoader.get("ACCESS_TOKEN"));
        this.preferenceClient = new PreferenceClient();
    }


    @PostMapping("/create")
    public ResponseEntity<?> createPreference(@RequestBody OrderRequest orderRequest) {
        try {
            // Crear el objeto de Preferencia
            PreferenceRequest preferenceRequest = buildPreferenceRequest(orderRequest);

            // Crear la preferencia en Mercado Pago
            Preference preference = preferenceClient.create(preferenceRequest);

            // Retornar el ID de preferencia y URLs
            CheckoutResponse response = new CheckoutResponse();
            response.setPreferenceId(preference.getId());
            response.setInitPoint(preference.getInitPoint());
            response.setSandboxInitPoint(preference.getSandboxInitPoint());

            return ResponseEntity.ok(response);
        } catch (MPException | MPApiException e) {
            return ResponseEntity.badRequest().body("Error al crear la preferencia: " + e.getMessage());
        }
    }

    private PreferenceRequest buildPreferenceRequest(OrderRequest orderRequest) {
        // Crear una lista de ítems para la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (OrderItem orderItem : orderRequest.getItems()) {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(orderItem.getTitle())
                    .quantity(orderItem.getQuantity())
                    .unitPrice(orderItem.getUnitPrice())
                    .currencyId("COP") // Cambiar según tu país (COP para Colombia)
                    .build();

            items.add(item);
        }

        // Configurar las URLs de retorno (back_urls)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(orderRequest.getSuccessUrl())
                .failure(orderRequest.getFailureUrl())
                .pending(orderRequest.getPendingUrl())
                .build();

        // Construir la solicitud de preferencia
        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(orderRequest.getExternalReference())
                .notificationUrl(orderRequest.getNotificationUrl())
                .build();
    }
}