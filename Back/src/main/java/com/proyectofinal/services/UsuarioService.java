//package com.proyectofinal.services;
//
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.proyectofinal.dao.UsuarioRepository;
//import com.proyectofinal.dominio.Usuario;
//
//@Service
//public class UsuarioService {
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    public List<Usuario> obtenerTodos() {
//        return usuarioRepository.findAll();
//    }
//     
//    public Optional<Usuario> login(@RequestParam String email) { 
//    	return usuarioRepository.findByEmail(email);
//    }
//
//    public Usuario guardarUsuario(Usuario usuario) {
//        return usuarioRepository.save(usuario);
//    }
//
//    public Usuario obtenerPorId(Long id) {
//        return usuarioRepository.findById(id).orElse(null);
//    }
//
//    public void eliminarUsuario(Long id) {
//        usuarioRepository.deleteById(id);
//    }
//
//	public boolean existeUsuario(String email) {
//	    return usuarioRepository.findByEmail(email).isPresent();
//	}
//
//}
//
//

package com.proyectofinal.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.Usuario;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public List<Usuario> obtenerTodos() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> login(String email, String password) {
		Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
		if (usuario.isPresent() && passwordMatches(password, usuario.get().getPassword())) {
			return usuario; // Retorna el usuario si las credenciales son válidas
		}
		return Optional.empty(); // Retorna vacío si las credenciales son inválidas
	}

	   public Optional<Usuario> findById(Long id) {
	        return usuarioRepository.findById(id);
	    }
	
	private boolean passwordMatches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword); // Compara la contraseña en texto plano con laencriptada
																		
	}

	public Usuario guardarUsuario(Usuario usuario) {
		usuario.setPassword(encryptPassword(usuario.getPassword())); // Encripta la contraseña
		return usuarioRepository.save(usuario);
	}

	public String encryptPassword(String rawPassword) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(rawPassword); // Encripta la contraseña
	}

	public Usuario obtenerPorId(Long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public void eliminarUsuario(Long id) {
		if (usuarioRepository.existsById(id)) {
			usuarioRepository.deleteById(id);
		} else {
			throw new RuntimeException("Usuario no encontrado");
		}
	}

	public boolean existeUsuario(String email) {
		return usuarioRepository.findByEmail(email).isPresent();
	}
}
