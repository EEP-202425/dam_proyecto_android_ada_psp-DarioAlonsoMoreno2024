package com.proyectofinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.dto.LoginResponse;
import com.proyectofinal.dto.UserDTO;
import com.proyectofinal.dto.UserMeDTO;
import com.proyectofinal.security.JwtService;
import com.proyectofinal.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody UserDTO usuarioDTO) {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getPassword()));
		} catch (AuthenticationException ex) {
			return ResponseEntity.status(401).build();
		}

		return usuarioRepository.findByEmail(usuarioDTO.getEmail()).map(u -> {
			String tokenJwt = jwtService.generateToken(u.getEmail(), u.getId());
			return ResponseEntity.ok(new LoginResponse(tokenJwt, u.getId()));
		}).orElse(ResponseEntity.status(401).build());
	}

	@PostMapping("/registro")
	public ResponseEntity<?> registrar(@RequestBody UserDTO usuarioDTO) {
		if (usuarioService.existeUsuario(usuarioDTO.getEmail())) {
			return ResponseEntity.badRequest().body("El usuario ya existe");
		}
		Usuario nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO.toUsuario());
		return ResponseEntity.status(201).body(nuevoUsuario);
	}

	// NUEVO: validar token + obtener usuario autenticado
	@GetMapping("/me")
	public ResponseEntity<UserMeDTO> me() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName(); // viene del JWT (subject/email)

		return usuarioRepository.findByEmail(email)
				.map(u -> ResponseEntity.ok(new UserMeDTO(u.getId(), u.getEmail(), u.getName(), u.getLastName())))
				.orElse(ResponseEntity.status(404).build());
	}
}
