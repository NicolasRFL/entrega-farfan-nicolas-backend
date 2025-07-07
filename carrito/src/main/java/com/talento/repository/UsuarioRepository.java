package com.talento.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.talento.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar un usuario por su email
    Optional<Usuario> findByEmail(String email);

    // Método para buscar un usuario por su nombre
    List<Usuario> findByNombre(String nombre);
    
    // Método para verificar si existe un usuario por su email
    boolean existsByEmail(String email);
    
    // Método para verificar si existe un usuario por su nombre
    boolean existsByNombre(String nombre);

}