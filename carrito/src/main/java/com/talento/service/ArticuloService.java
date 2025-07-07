package com.talento.service;
import com.talento.model.Articulo;
import java.util.List;
import java.util.Optional;

public interface ArticuloService {
    List<Articulo> listarArticulos(); // Lista todos los artículos
    Optional<Articulo> obtenerArticuloPorId(Long id); // Obtiene un artículo por ID
    Articulo guardarArticulo(Articulo articulo); // Guarda un nuevo artículo
    Articulo actualizarArticulo(Long id, Articulo articulo); // Actualiza un artículo existente
    void eliminarArticulo(Long id); // Elimina un artículo
}