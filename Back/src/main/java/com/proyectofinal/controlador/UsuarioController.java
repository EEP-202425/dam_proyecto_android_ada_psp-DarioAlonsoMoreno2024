//package com.proyectofinal.controlador;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.proyectofinal.dominio.Usuario;
//import com.proyectofinal.services.UsuarioService;
//
//@RestController
//@RequestMapping("/api/Usuarios")
//public class UsuarioController {
//
//	@Autowired
//	private UsuarioService usuarioService;
//	
//	  @PostMapping("/login")
//	    public String login(@RequestParam String email) {
//	        Optional<Usuario> usuario = usuarioService.login(email);
//	        return usuario.map(u -> "Bienvenido, " + u.getName())
//	                      .orElse("Usuario no encontrado");
//	    } 
//	  
//	  @PostMapping("/registro")
//	  public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
//	      if (usuarioService.existeUsuario(usuario.getEmail())) {
//	          return ResponseEntity.badRequest().body("El usuario ya existe");
//	      }
//	      Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
//	      return ResponseEntity.ok(nuevoUsuario);
//	  }
//}


package com.proyectofinal.controlador;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.dto.LoginResponse;
import com.proyectofinal.dto.UserDTO;
import com.proyectofinal.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO usuarioDTO) {
        return usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword())
          .map(u -> {
            // Genera un token; aquí un UUID de ejemplo
            String token = UUID.randomUUID().toString();
            return ResponseEntity
                     .ok(new LoginResponse(token, u.getId()));
          })
          .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody UserDTO usuarioDTO) {
        if (usuarioService.existeUsuario(usuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO.toUsuario()); // Método para convertir DTO a entidad
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
}