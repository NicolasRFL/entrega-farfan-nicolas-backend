package com.talento.service;
import com.talento.dto.LoginDTO;
import com.talento.model.Usuario;
import java.util.Optional;

public interface UsuarioService {
    Usuario registrarCliente(Usuario usuario);
    Usuario registrarAdministrador(Usuario usuario);
    Optional<Usuario> obtenerPorEmail(String email);
    Usuario guardar(Usuario usuario);
    Usuario login(LoginDTO loginDTO);
}