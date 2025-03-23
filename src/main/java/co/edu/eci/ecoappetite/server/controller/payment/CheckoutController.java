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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final PreferenceClient preferenceClient;

    public CheckoutController() {
        // Inicializar Mercado Pago con el Access Token
        String accessToken = ConfigLoader.get("ACCESS_TOKEN");
        System.out.println("Usando token: " + (accessToken != null ? accessToken.substring(0, 5) + "..." : "null"));

        MercadoPagoConfig.setAccessToken(accessToken);

        this.preferenceClient = new PreferenceClient();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPreference(@RequestBody OrderRequest orderRequest) {
        try {
            // Validar que haya items
            if (orderRequest.getItems() == null || orderRequest.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("La orden debe contener al menos un item");
            }

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
        } catch (MPApiException e) {
            System.out.println("Response Code: " + e.getApiResponse().getStatusCode());
            System.out.println("Response Body: " + e.getApiResponse().getContent());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error de API Mercado Pago: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error general de Mercado Pago: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error inesperado: " + e.getMessage());
        }
    }

    private PreferenceRequest buildPreferenceRequest(OrderRequest orderRequest) {
        // Crear una lista de ítems para la preferencia
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (OrderItem orderItem : orderRequest.getItems()) {
            String title = (orderItem.getTitle() != null && !orderItem.getTitle().isEmpty())
                    ? orderItem.getTitle()
                    : "Producto sin nombre";

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(orderItem.getQuantity())
                    .unitPrice(orderItem.getUnitPrice())
                    .currencyId("COP")
                    .build();

            items.add(item);
        }

        // Configurar las URLs de retorno
        //String baseUrl = "https://ecoappetite.ngrok-free.app"; // Cambiar por tu URL de ngrok
        String baseUrl = "https://ecoappetite.ip-ddns.com";
        String complementUrl = "/api/payment";

        String successUrl = (orderRequest.getSuccessUrl() != null && !orderRequest.getSuccessUrl().isEmpty())
                ? orderRequest.getSuccessUrl()
                : baseUrl + complementUrl +"/success";
        String failureUrl = (orderRequest.getFailureUrl() != null && !orderRequest.getFailureUrl().isEmpty())
                ? orderRequest.getFailureUrl()
                : baseUrl + complementUrl + "/failure";
        String pendingUrl = (orderRequest.getPendingUrl() != null && !orderRequest.getPendingUrl().isEmpty())
                ? orderRequest.getPendingUrl()
                : baseUrl + complementUrl + "/pending";

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(successUrl)
                .failure(failureUrl)
                .pending(pendingUrl)
                .build();

        // Generar una referencia única si no se proporciona
        String externalReference = (orderRequest.getExternalReference() != null && !orderRequest.getExternalReference().isEmpty())
                ? orderRequest.getExternalReference()
                : "ref-" + UUID.randomUUID().toString();

        // Determinar la URL de notificación válida
        String notificationUrl = null;
        if (orderRequest.getNotificationUrl() != null && !orderRequest.getNotificationUrl().isEmpty()
                && (orderRequest.getNotificationUrl().startsWith("https://") || orderRequest.getNotificationUrl().startsWith("http://"))) {
            notificationUrl = orderRequest.getNotificationUrl();
        } else if (baseUrl != null && !baseUrl.isEmpty()) {
            notificationUrl = baseUrl + complementUrl + "/notifications";
        }

        // Construir la preferencia usando el builder
        PreferenceRequest.PreferenceRequestBuilder preferenceRequestBuilder = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .externalReference(externalReference);

        // Agregar la URL de notificación si es válida
        if (notificationUrl != null) {
            preferenceRequestBuilder.notificationUrl(notificationUrl);
        }

        // Construir el objeto PreferenceRequest final
        return preferenceRequestBuilder.build();
    }
}
