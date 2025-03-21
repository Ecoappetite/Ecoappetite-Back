package com.ecoappetite.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Clase que representa el carrito de compras de un usuario en un restaurante.
 * Un carrito está asociado a un usuario y a un restaurante específico.
 */
@Entity
@Table(name = "carrito")
public class Carrito {

    /** Identificador único del carrito */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Usuario al que pertenece el carrito */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /** Restaurante asociado al carrito */
    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    /** Lista de productos agregados al carrito */
    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items;

    /** Constructor vacío requerido por JPA */
    public Carrito() {
    }

    /**
     * Constructor para crear un carrito asociado a un usuario y a un restaurante.
     *
     * @param usuario     Usuario propietario del carrito
     * @param restaurante Restaurante donde se realiza el pedido
     */
    public Carrito(Usuario usuario, Restaurante restaurante) {
        this.usuario = usuario;
        this.restaurante = restaurante;
    }

    /** @return Identificador único del carrito */
    public Long getId() {
        return id;
    }

    /** @param id Identificador único del carrito */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return Usuario propietario del carrito */
    public Usuario getUsuario() {
        return usuario;
    }

    /** @param usuario Usuario propietario del carrito */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /** @return Restaurante donde se realiza el pedido */
    public Restaurante getRestaurante() {
        return restaurante;
    }

    /** @param restaurante Restaurante donde se realiza el pedido */
    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    /** @return Lista de productos en el carrito */
    public List<ItemCarrito> getItems() {
        return items;
    }

    /** @param items Lista de productos en el carrito */
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }
}
