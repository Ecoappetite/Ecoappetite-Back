package co.edu.eci.ecoappetite.server.domain.dto;


import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItem {
    private String title;
    private Integer quantity;
    private BigDecimal unitPrice;
}