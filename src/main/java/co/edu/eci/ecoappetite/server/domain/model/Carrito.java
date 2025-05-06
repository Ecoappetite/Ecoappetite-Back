package co.edu.eci.ecoappetite.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    private String consumidorId;
    private List<ItemCarrito> items;
    private List<String> productos;
    private double total;



    public List<String> getProductos() {
        return productos;
    }

    public double getTotal() {
        return total;
    }

    public String getConsumidorId() {
        return consumidorId;
    }

    public void setConsumidorId(String usuarioId) {
        this.consumidorId = usuarioId;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.total = 0.0;
    }



}
