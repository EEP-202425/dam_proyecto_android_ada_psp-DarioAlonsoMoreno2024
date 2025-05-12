package com.proyectofinal.dto;

import com.proyectofinal.dominio.Usuario;

public class UserDTO {
	private String email;
	private String password;
	private String name; // Si es necesario
    private String lastName;   // ← falta
    private Integer edad;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(this.email);
		usuario.setPassword(this.password); // Asegúrate de encriptar la contraseña
		usuario.setName(this.name);
		usuario.setEdad(this.edad);
		usuario.setLastName(this.lastName);
		
		return usuario;
	}
}