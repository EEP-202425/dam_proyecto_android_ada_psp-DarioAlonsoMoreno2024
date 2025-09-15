package com.proyectofinal.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.proyectofinal.dominio.Producto;
import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.dto.ProductoCreateDTO;
import com.proyectofinal.dto.ProductoDTO;
import com.proyectofinal.dto.ProductoConStockDTO;
import com.proyectofinal.services.ProductoService;
import com.proyectofinal.services.UsuarioService;

@RestController
@RequestMapping("/api/producto")
public class ProductoController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    @Autowired
    public ProductoController(ProductoService productoService,
                              UsuarioService usuarioService) {
        this.productoService = productoService;
        this.usuarioService  = usuarioService;
    }

    @GetMapping(path = "/stock", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductoConStockDTO> obtenerProductosConStock() {
        return productoService.obtenerProductosConStock();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductoDTO> obtenerTodos() {
        return productoService.obtenerTodosLosProductos().stream()
            .map(p -> new ProductoDTO(
                p.getId(),
                p.getUsuario().getId(),
                p.getPrecio(),
                p.getNombre(),
                p.getYear()
            ))
            .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductoDTO obtenerPorId(@PathVariable Long id) {
        Producto p = productoService.obtenerProducto(id);
        return new ProductoDTO(
            p.getId(),
            p.getUsuario().getId(),
            p.getPrecio(),
            p.getNombre(),
            p.getYear()
        );
    }

    @PostMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoCreateDTO dto) {
        Usuario u = usuarioService.findById(dto.getUsuarioId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Usuario no existe")
            );

        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setYear(dto.getYear());
        p.setUsuario(u);

        Producto guardado = productoService.crearProducto(p);
        ProductoDTO salida = new ProductoDTO(
            guardado.getId(),
            u.getId(),
            guardado.getPrecio(),
            guardado.getNombre(),
            guardado.getYear()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(salida);
    }

    @PutMapping(
        path = "/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ProductoDTO actualizarProducto(
        @PathVariable Long id,
        @RequestBody ProductoCreateDTO dto
    ) {
        Usuario u = usuarioService.findById(dto.getUsuarioId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Usuario no existe")
            );

        Producto p = new Producto();
        p.setId(id);
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setYear(dto.getYear());
        p.setUsuario(u);

        Producto actualizado = productoService.actualizarProducto(p, id);
        return new ProductoDTO(
            actualizado.getId(),
            u.getId(),
            actualizado.getPrecio(),
            actualizado.getNombre(),
            actualizado.getYear()
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
