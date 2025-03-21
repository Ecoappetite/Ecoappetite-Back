package com.ecoappetite.repository;

import com.ecoappetite.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Carrito.
 * Proporciona métodos para interactuar con la base de datos.
 */
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
}
