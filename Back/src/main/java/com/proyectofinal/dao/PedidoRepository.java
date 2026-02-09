package com.proyectofinal.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyectofinal.dominio.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Ordenar por id desc (más reciente primero)
    List<Pedido> findByUsuario_IdOrderByIdDesc(Long usuarioId);

    // Si prefieres por fecha (también válido):
     List<Pedido> findByUsuario_IdOrderByFechaDesc(Long usuarioId);
}
