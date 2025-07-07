package com.talento.model;

import java.util.List;

import jakarta.persistence.*;

// Indica que esta clase es una entidad JPA
@Entity
@Table(name = "pedido") // Mapea a la tabla "pedido"
public class Pedido {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    private String dniCliente;
    private String fechaPedido;
    private String fechaEntrega;

    private Double cantidad;

    @ManyToMany
    @JoinTable(
        name = "pedido_articulo", // tabla intermedia
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "articulo_id")
    )
    private List<Articulo> articulos;

    public Pedido() {}

    public Pedido(Long id, Usuario usuario, String dniCliente, String fechaPedido, String fechaEntrega, Double cantidad, List<Articulo> articulos) {
        this.id = id;
        this.usuario = usuario;
        this.dniCliente = dniCliente;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.cantidad = cantidad;
        this.articulos = articulos;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }
    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getDniCliente() { return dniCliente; }
    public void setDniCliente(String dniCliente) { this.dniCliente = dniCliente; }
    public String getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(String fechaPedido) { this.fechaPedido = fechaPedido; }
    public String getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(String fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public Double getCantidad() { return cantidad; }
    public void setCantidad(Double cantidad) { this.cantidad = cantidad; }
}
