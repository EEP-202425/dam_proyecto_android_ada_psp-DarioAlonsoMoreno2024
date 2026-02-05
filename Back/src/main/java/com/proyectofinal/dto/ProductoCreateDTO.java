//package com.proyectofinal.dto;
//
//public class ProductoCreateDTO {
//	private String nombre;
//	private double precio;
//	private int year;
//	   private Long usuarioId;
//
//	public String getNombre() {
//		return nombre;
//	}
//
//	public void setNombre(String nombre) {
//		this.nombre = nombre;
//	}
//
//	public double getPrecio() {
//		return precio;
//	}
//
//	public void setPrecio(double precio) {
//		this.precio = precio;
//	}
//
//	public int getYear() {
//		return year;
//	}
//
//	public void setYear(int year) {
//		this.year = year;
//	}
//
//	public Long getUsuarioId() {
//		return usuarioId;
//	}
//
//	public void setUsuarioId(Long usuarioId) {
//		this.usuarioId = usuarioId;
//	}
//	
//	
//
//}




package com.proyectofinal.dto;

public class ProductoCreateDTO {

    private Long usuarioId;
    private String nombre;
    private Double precio;
    private Integer year;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}