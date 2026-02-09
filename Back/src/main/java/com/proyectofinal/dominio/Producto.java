package com.proyectofinal.dominio;

import jakarta.persistence.*;

@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private double precio;

	// Si este "year" te lía, luego lo eliminamos. Por ahora no lo tocamos.
	private int year;

	@Column(nullable = false)
	private String marca;

	@Column(nullable = false)
	private int stock = 0;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Usuario usuario;

	public Producto() {
	}

	public Producto(String nombre, double precio, int year, String marca, int stock, Usuario usuario) {
		this.nombre = nombre;
		this.precio = precio;
		this.year = year;
		this.marca = marca;
		this.stock = stock;
		this.usuario = usuario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
