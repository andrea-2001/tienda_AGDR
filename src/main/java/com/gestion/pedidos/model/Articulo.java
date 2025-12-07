package com.gestion.pedidos.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    @Column(name = "stock", nullable = false)
    private int stock;

    @OneToMany(mappedBy = "articulo", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticuloCompra> articulosCompra = new HashSet<>();

    public Articulo() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Set<ArticuloCompra> getArticulosCompra() { return articulosCompra; }
    public void setArticulosCompra(Set<ArticuloCompra> articulosCompra) { this.articulosCompra = articulosCompra; }

    // Métodos para mantener la relación bidireccional
    public void addArticuloCompra(ArticuloCompra ac) {
        articulosCompra.add(ac);
        ac.setArticulo(this);
    }

    public void removeArticuloCompra(ArticuloCompra ac) {
        articulosCompra.remove(ac);
        ac.setArticulo(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Articulo)) return false;
        Articulo articulo = (Articulo) o;
        return id == articulo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                '}';
    }
}
