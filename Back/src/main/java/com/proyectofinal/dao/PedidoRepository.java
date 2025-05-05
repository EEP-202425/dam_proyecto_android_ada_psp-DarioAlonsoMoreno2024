
package com.proyectofinal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.proyectofinal.dominio.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
