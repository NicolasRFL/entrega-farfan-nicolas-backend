package com.talento.controller;

import com.talento.model.Articulo;
import com.talento.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/articulos")
public class ArticuloController {
    private final ArticuloService articuloService;

    @Autowired
    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @GetMapping
    public List<Articulo> listar() {
        return articuloService.listarArticulos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtenerPorId(@PathVariable Long id) {
        return articuloService.obtenerArticuloPorId(id)
                .map(ResponseEntity::ok) // Devuelve 200 si lo encuentra
                .orElse(ResponseEntity.notFound().build()); // Devuelve 404 si no
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Articulo crear(@RequestBody Articulo articulo) {
        return articuloService.guardarArticulo(articulo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (articuloService.obtenerArticuloPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }
        articuloService.eliminarArticulo(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizar(@PathVariable Long id, @RequestBody Articulo articulo) {
        Articulo actualizado = articuloService.actualizarArticulo(id, articulo);
        return ResponseEntity.ok(actualizado);
    }
}
