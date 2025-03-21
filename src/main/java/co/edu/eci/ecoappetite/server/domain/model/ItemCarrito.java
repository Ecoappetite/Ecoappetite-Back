package com.ecoappetite.model;

import jakarta.persistence.*;

/**
 * Clase que representa un ítem dentro del carrito de compras.
 * Contiene información sobre el producto y la cantidad seleccionada.
 */
@Entity
@Table(name = "item_carrito")
public class ItemCarrito {

    /** Identificador único del ítem en el carrito */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Carrito al que pertenece este ítem */
    @ManyToOne
    @JoinColumn(name = "carrito_id", nullable = false)
    private Carrito carrito;

    /** Producto agregado al carrito */
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    /** Cantidad del producto en el carrito */
    private int cantidad;

    /** Constructor vacío requerido por JPA */
    public ItemCarrito() {
    }

    /**
     * Constructor con parámetros para crear un ítem en el carrito.
     *
     * @param carrito  Carrito al que pertenece el ítem
     * @param producto Producto agregado al carrito
     * @param cantidad Cantidad del producto seleccionado
     */
    public ItemCarrito(Carrito carrito, Producto producto, int cantidad) {
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    /** @return Identificador único del ítem */
    public Long getId() {
        return id;
    }

    /** @param id Identificador único del ítem */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return Carrito al que pertenece el ítem */
    public Carrito getCarrito() {
        return carrito;
    }

    /** @param carrito Carrito al que pertenece el ítem */
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    /** @return Producto agregado al carrito */
    public Producto getProducto() {
        return producto;
    }

    /** @param producto Producto agregado al carrito */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /** @return Cantidad del producto en el carrito */
    public int getCantidad() {
        return cantidad;
    }

    /** @param cantidad Cantidad del producto en el carrito */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
