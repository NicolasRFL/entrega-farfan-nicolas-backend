package com.talento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.talento.model.Pedido;
import com.talento.service.PedidoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id)
                .map(ResponseEntity::ok) // Devuelve 200 si lo encuentra
                .orElse(ResponseEntity.notFound().build()); // Devuelve 404 si no
    }

    @PostMapping
    public Pedido crear(@RequestBody Pedido pedido) {
        return pedidoService.guardarPedido(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Long id, @RequestBody Pedido pedido) {
        if (pedidoService.obtenerPedidoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }
        Pedido pedidoActualizado = pedidoService.actualizarPedido(id, pedido);
        return ResponseEntity.ok(pedidoActualizado); // Devuelve 200 con el pedido actualizado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (pedidoService.obtenerPedidoPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
