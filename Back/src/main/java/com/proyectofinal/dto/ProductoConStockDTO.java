package com.proyectofinal.dto;

public class ProductoConStockDTO {
    private String modelo;
    private int precio;
    private int stock;

    public ProductoConStockDTO(String modelo, int precio, int stock) {
        this.modelo = modelo;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public String getModelo() {
        return modelo;
    }

    public int getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}
