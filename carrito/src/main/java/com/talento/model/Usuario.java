package com.talento.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String contraseña;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Usuario() {}
    public Usuario(Long id, String nombre, String email, String contraseña, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    public String getContraseña() { return contraseña; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}