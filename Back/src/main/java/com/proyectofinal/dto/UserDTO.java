package com.proyectofinal.dto;

import com.proyectofinal.dominio.Usuario;

public class UserDTO {
	private String email;
	private String password;
	private String name; // Si es necesario

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

	public Usuario toUsuario() {
		Usuario usuario = new Usuario();
		usuario.setEmail(this.email);
	//	usuario.setPassword(this.password); // Asegúrate de encriptar la contraseña
		usuario.setName(this.name);
		return usuario;
	}
}