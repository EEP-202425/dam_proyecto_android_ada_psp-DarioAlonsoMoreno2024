package com.proyectofinal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyectofinal.dominio.Producto;

@Repository
public interface ProductRepository extends JpaRepository<Producto, Long> {
    // Puedes agregar métodos personalizados aquí si es necesario
}