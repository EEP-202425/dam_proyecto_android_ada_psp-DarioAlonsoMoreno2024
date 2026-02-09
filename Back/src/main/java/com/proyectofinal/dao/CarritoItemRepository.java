package com.proyectofinal.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.dominio.CarritoItem;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {

	List<CarritoItem> findByUsuario_Id(Long usuarioId);

	Optional<CarritoItem> findByUsuario_IdAndProducto_Id(Long usuarioId, Long productoId);

	void deleteByUsuario_Id(Long usuarioId);
}
