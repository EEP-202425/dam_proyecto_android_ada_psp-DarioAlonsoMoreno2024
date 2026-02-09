package com.proyectofinal.dto;

public class ProductoDTO {

	private Long id;
	private Long userId;
	private double precio;
	private String nombre;
	private int year;

	private String marca;
	private int stock;

	public ProductoDTO() {
	}

	public ProductoDTO(Long id, Long userId, double precio, String nombre, int year, String marca, int stock) {
		this.id = id;
		this.userId = userId;
		this.precio = precio;
		this.nombre = nombre;
		this.year = year;
		this.marca = marca;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}