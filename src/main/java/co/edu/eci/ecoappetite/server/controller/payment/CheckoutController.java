package co.edu.eci.ecoappetite.server.controller.payment;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final PreferenceClient preferenceClient;

    public CheckoutController() {
        // Obtener Access Token desde ConfigLoader
        String accessToken = ConfigLoader.get("ACCESS_TOKEN");

        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalStateException("ACCESS_TOKEN no está configurado correctamente.");
        }
        System.out.println("Usando Access Token: " + accessToken.substring(0, 5) + "...");

        // Inicializar MercadoPago
        MercadoPagoConfig.setAccessToken(accessToken);
        this.preferenceClient = new PreferenceClient();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPreference(@RequestBody OrderRequest orderRequest) {
        try {

            // Re-initialize MercadoPago config before each request
            String accessToken = ConfigLoader.get("ACCESS_TOKEN");
            MercadoPagoConfig.setAccessToken(accessToken);
            if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: La orden debe contener al menos un item.");
            }
	    
	    System.out.println("hola");	
            // Crear preferencia
            PreferenceRequest preferenceRequest = buildPreferenceRequest(orderRequest);
            Preference preference = preferenceClient.create(preferenceRequest);
            System.out.println("hola2"); 
            // Construir respuesta
            CheckoutResponse response = new CheckoutResponse();
            response.setPreferenceId(preference.getId());
            response.setInitPoint(preference.getInitPoint());
            response.setSandboxInitPoint(preference.getSandboxInitPoint());

            System.out.println("Preferencia creada: " + preference.getId());
            return ResponseEntity.ok(response);

        } catch (MPApiException e) {
            System.err.println("API Mercado Pago error: " + e.getApiResponse().getContent());
            return ResponseEntity.status(e.getApiResponse().getStatusCode()).body("Error Mercado Pago: " + e.getApiResponse().getContent());

        } catch (MPException e) {
            System.err.println("Error general de Mercado Pago: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error general de Mercado Pago: " + e.getMessage());

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error inesperado: " + e.getMessage());
        }
    }

    private PreferenceRequest buildPreferenceRequest(OrderRequest orderRequest) {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (OrderItem orderItem : orderRequest.getItems()) {
            String title = (orderItem.getTitle() != null && !orderItem.getTitle().isEmpty())
                    ? orderItem.getTitle()
                    : "Producto sin nombre";

            items.add(PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(orderItem.getQuantity())
                    .unitPrice(orderItem.getUnitPrice())
                    .currencyId("COP")
                    .build());
        }

        // Configurar URLs
        String baseUrl = "https://ecoappetite.ip-ddns.com";
        String complementUrl = "/api/payment";

        String successUrl = baseUrl + complementUrl + "/success";
        String failureUrl = baseUrl + complementUrl + "/failure";
        String pendingUrl = baseUrl + complementUrl + "/pending";

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(successUrl)
                .failure(failureUrl)
                .pending(pendingUrl)
                .build();

        // Referencia externa
        String externalReference = (orderRequest.getExternalReference() != null && !orderRequest.getExternalReference().isEmpty())
                ? orderRequest.getExternalReference()
                : "ref-" + UUID.randomUUID();

        // Notificación de pago
        String notificationUrl = baseUrl + complementUrl + "/notifications";

        return PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(externalReference)
                .notificationUrl(notificationUrl)
                .build();
    }
}
