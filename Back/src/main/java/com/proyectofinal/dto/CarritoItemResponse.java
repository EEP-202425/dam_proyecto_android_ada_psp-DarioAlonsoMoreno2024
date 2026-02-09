package com.proyectofinal.dto;

public class CarritoItemResponse {

	private Long itemId;
	private Long productoId;
	private String nombre;
	private String marca;
	private double precio;
	private int cantidad;
	private double subtotal;

	public CarritoItemResponse(Long itemId, Long productoId, String nombre, String marca, double precio, int cantidad,
			double subtotal) {
		this.itemId = itemId;
		this.productoId = productoId;
		this.nombre = nombre;
		this.marca = marca;
		this.precio = precio;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
	}

	public Long getItemId() {
		return itemId;
	}

	public Long getProductoId() {
		return productoId;
	}

	public String getNombre() {
		return nombre;
	}

	public String getMarca() {
		return marca;
	}

	public double getPrecio() {
		return precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public double getSubtotal() {
		return subtotal;
	}
}
