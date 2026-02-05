//
//
//package com.proyectofinal.controlador;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.proyectofinal.dominio.Usuario;
//import com.proyectofinal.dto.LoginResponse;
//import com.proyectofinal.dto.UserDTO;
//import com.proyectofinal.services.UsuarioService;
//
//@RestController
//@RequestMapping("/api/usuarios")
//public class UsuarioController {
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO usuarioDTO) {
//        return usuarioService.login(usuarioDTO.getEmail(), usuarioDTO.getPassword())
//          .map(u -> {
//            // Genera un token; aquí un UUID de ejemplo
//            String token = UUID.randomUUID().toString();
//            return ResponseEntity
//                     .ok(new LoginResponse(token, u.getId()));
//          })
//          .orElse(ResponseEntity.status(401).build());
//    }
//
//    @PostMapping("/registro")
//    public ResponseEntity<?> registrar(@RequestBody UserDTO usuarioDTO) {
//        if (usuarioService.existeUsuario(usuarioDTO.getEmail())) {
//            return ResponseEntity.badRequest().body("El usuario ya existe");
//        }
//        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO.toUsuario()); // Método para convertir DTO a entidad
//        return ResponseEntity.status(201).body(nuevoUsuario);
//    }
//}

package com.proyectofinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.dto.LoginResponse;
import com.proyectofinal.dto.UserDTO;
import com.proyectofinal.security.JwtService;
import com.proyectofinal.services.UsuarioService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // NUEVO: autentica contra Spring Security (UserDetailsService + BCrypt)
    @Autowired
    private AuthenticationManager authenticationManager;

    // NUEVO: genera JWT real
    @Autowired
    private JwtService jwtService;

    // NUEVO: para obtener el id del usuario y meterlo en el token/response
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO usuarioDTO) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    usuarioDTO.getEmail(),
                    usuarioDTO.getPassword()
                )
            );
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).build();
        }

        return usuarioRepository.findByEmail(usuarioDTO.getEmail())
            .map(u -> {
                String tokenJwt = jwtService.generateToken(u.getEmail(), u.getId());
                return ResponseEntity.ok(new LoginResponse(tokenJwt, u.getId()));
            })
            .orElse(ResponseEntity.status(401).build());
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody UserDTO usuarioDTO) {
        if (usuarioService.existeUsuario(usuarioDTO.getEmail())) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuarioDTO.toUsuario());
        return ResponseEntity.status(201).body(nuevoUsuario);
    }
}