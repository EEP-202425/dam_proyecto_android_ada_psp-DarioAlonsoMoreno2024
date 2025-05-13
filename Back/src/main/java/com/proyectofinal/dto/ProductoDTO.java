package com.proyectofinal.dto;

public class ProductoDTO {
	private Long id;
	private Long userId; // coincidirá con @SerializedName("userId") en Android
	private double precio;
	private String nombre; // coincide con @SerializedName("nombre")→model en Android
	private int year;

	public ProductoDTO() {
	}

	public ProductoDTO(Long id, Long userId, double precio, String nombre, int year) {
		this.id = id;
		this.userId = userId;
		this.precio = precio;
		this.nombre = nombre;
		this.year = year;
	}

	// Getters y setters
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
}
