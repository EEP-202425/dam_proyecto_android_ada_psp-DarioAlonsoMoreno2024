package com.proyectofinal.dominio;

import jakarta.persistence.*;

@Entity
@Table(name = "carrito_item", uniqueConstraints = @UniqueConstraint(columnNames = { "usuario_id", "producto_id" }))
public class CarritoItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	private Usuario usuario;

	@ManyToOne(optional = false)
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	@Column(nullable = false)
	private Integer cantidad = 1;

	public CarritoItem() {
	}

	public CarritoItem(Usuario usuario, Producto producto, Integer cantidad) {
		this.usuario = usuario;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
