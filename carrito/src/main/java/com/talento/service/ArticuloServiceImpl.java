package com.talento.service;

import com.talento.model.Articulo;
import com.talento.model.Pedido;
import com.talento.repository.ArticuloRepository;
import com.talento.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca la clase como servicio de Spring
public class ArticuloServiceImpl implements ArticuloService {
    private final ArticuloRepository articuloRepository;
    private final PedidoRepository pedidoRepository;

    @Autowired
    public ArticuloServiceImpl(ArticuloRepository articuloRepository, PedidoRepository pedidoRepository) {
        this.articuloRepository = articuloRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
    }

    public Articulo guardarArticulo(Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    public Articulo actualizarArticulo(Long id, Articulo articulo) {
        articulo.setId(id);
        return articuloRepository.save(articulo);
    }

    public void eliminarArticulo(Long id) {
        List<Pedido> pedidos = pedidoRepository.findAllByArticulos_Id(id);
        for (Pedido pedido : pedidos) {
            pedido.getArticulos().removeIf(articulo -> articulo.getId().equals(id));
        }
        pedidoRepository.saveAll(pedidos);
        articuloRepository.deleteById(id);
    }
}