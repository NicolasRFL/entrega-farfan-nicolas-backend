package com.talento.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.talento.model.Usuario;
import com.talento.dto.LoginDTO;
import com.talento.model.Rol;
import com.talento.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarCliente(Usuario usuario) {
        registrarUsuario(usuario, Rol.CLIENTE);
        return usuarioRepository.save(usuario);
    }

    private void registrarUsuario(Usuario usuario, Rol rol) {
        usuarioRepository.findByEmail(usuario.getEmail())
            .ifPresent(u -> {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado");
            });
        usuario.setRol(rol);
        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
    }

    public Usuario registrarAdministrador(Usuario usuario) {
        registrarUsuario(usuario, Rol.ADMIN);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario login(LoginDTO loginDTO) {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email no registrado"));        
        System.out.println("Rol " + usuario.getRol());
        if (!passwordEncoder.matches(loginDTO.getContraseña(), usuario.getContraseña())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }

        return usuario;
    }
}