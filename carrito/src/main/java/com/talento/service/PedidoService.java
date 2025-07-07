package com.talento.service;
import com.talento.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> listarPedidos(); // Lista todos los pedidos
    Optional<Pedido> obtenerPedidoPorId(Long id); // Obtiene un pedido por ID
    Pedido guardarPedido(Pedido pedido); // Guarda un nuevo pedido
    Pedido actualizarPedido(Long id, Pedido pedido); // Actualiza un pedido existente
    void eliminarPedido(Long id); // Elimina un pedido
}
