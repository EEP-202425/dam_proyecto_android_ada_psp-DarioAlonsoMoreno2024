package com.proyectofinal.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.services.UsuarioService;

@RestController
@RequestMapping("/api/Usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	  @PostMapping("/login")
	    public String login(@RequestParam String email) {
	        Optional<Usuario> usuario = usuarioService.login(email);
	        return usuario.map(u -> "Bienvenido, " + u.getName())
	                      .orElse("Usuario no encontrado");
	    }
	
	@PostMapping("/registro")
	public Usuario registrar(@RequestBody Usuario usuario) {
		return usuarioService.guardarUsuario(usuario);
	}
}
