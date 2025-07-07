package com.talento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talento.config.security.JwtService;
import com.talento.dto.LoginDTO;
import com.talento.model.Usuario;
import com.talento.service.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/registrar/cliente")
    public Usuario registrarCliente(@RequestBody Usuario usuario) {
        return usuarioService.registrarCliente(usuario);
    }

    @PostMapping("/registrar/admin")
    public Usuario registrarAdministrador(@RequestBody Usuario usuario) {
        return usuarioService.registrarAdministrador(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        return usuarioService.obtenerPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioService.login(loginDTO);
        String token = jwtService.generateToken(usuario); // generar token con datos del usuario
        return ResponseEntity.ok(Map.of("token", token));
    }
}
