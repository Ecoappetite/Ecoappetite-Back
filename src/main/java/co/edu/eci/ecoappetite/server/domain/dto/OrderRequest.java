package co.edu.eci.ecoappetite.server.domain.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class OrderRequest {
    private List<OrderItem> items;
    private String successUrl;
    private String failureUrl;
    private String pendingUrl;
    private String externalReference;
    private String notificationUrl;
}