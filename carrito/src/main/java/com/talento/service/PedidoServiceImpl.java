package com.talento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.talento.model.Articulo;
import com.talento.model.Pedido;
import com.talento.model.Usuario;
import com.talento.repository.ArticuloRepository;
import com.talento.repository.PedidoRepository;
import com.talento.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final ArticuloRepository articuloRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public PedidoServiceImpl(PedidoRepository pedidoRepository, ArticuloRepository articuloRepository, UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.articuloRepository = articuloRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Pedido> listarPedidos() {
        Usuario usuario = getUsuario();
        return pedidoRepository.findByUsuarioId(usuario.getId());
    }

    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido guardarPedido(Pedido pedido) {
        Usuario usuario = getUsuario();
        List<Articulo> articulosPersistidos = pedido.getArticulos().stream()
            .map(a -> articuloRepository.findById(a.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artículo con ID " + a.getId() + " no encontrado")))
            .toList();
        pedido.setDniCliente(usuario.getDni());
        pedido.setArticulos(articulosPersistidos);
        pedido.setUsuario(usuario);

        return pedidoRepository.save(pedido);
    }
    
    private Usuario getUsuario() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado"));
        return usuario;
    }

    public Pedido actualizarPedido(Long id, Pedido pedido) {        
        Usuario usuario = getUsuario();
        Pedido pedidoExistente = pedidoRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        
        // Verificar que el pedido pertenezca al usuario autenticado
        if (!pedidoExistente.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tenés permiso para modificar este pedido");
        }        
        List<Articulo> articulosPersistidos = pedido.getArticulos().stream()
            .map(a -> articuloRepository.findById(a.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Artículo con ID " + a.getId() + " no encontrado")))
            .toList();
        pedido.setArticulos(articulosPersistidos);
        pedido.setId(id);
        pedido.setUsuario(usuario);
        return pedidoRepository.save(pedido);
    }

    public void eliminarPedido(Long id) {                
        Usuario usuario = getUsuario();        
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        
        // Validar que el pedido pertenezca al usuario
        if (!pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tenés permiso para eliminar este pedido");
        }
        pedidoRepository.deleteById(id);
    }

}
