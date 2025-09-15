package com.proyectofinal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.dominio.Inventario;
import com.proyectofinal.dominio.Producto;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
	Optional<Inventario> findByProducto(Producto producto);

}
