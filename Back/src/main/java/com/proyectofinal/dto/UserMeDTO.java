package com.proyectofinal.dto;

public class UserMeDTO {

	private Long id;
	private String email;
	private String name;
	private String lastName;

	public UserMeDTO(Long id, String email, String name, String lastName) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.lastName = lastName;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}
}
