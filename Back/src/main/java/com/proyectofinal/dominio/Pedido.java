package com.proyectofinal.dominio;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Pedido() {
    	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
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
	
	
    
    
    }