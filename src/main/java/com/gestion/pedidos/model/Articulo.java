package com.gestion.pedidos.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
/**
 * Representa un artículo en el sistema de gestión de pedidos.
 * @author Andrea
 * @since 2025-12-15
 * @version 1.0
 */

@Entity
@Table(name = "articulo")
public class Articulo {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; 

    // Nombre del artículo, no nulo, máximo 150 caracteres
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre; 

    // Descripción opcional del artículo, hasta 500 caracteres
    @Column(name = "descripcion", length = 500)
    private String descripcion; 

    // Precio del artículo, no nulo
    @Column(name = "precio", nullable = false)
    private double precio; 

    // Stock disponible, no nulo
    @Column(name = "stock", nullable = false)
    private int stock; 

    // Relación 1:N con ArticuloCompra (tabla de unión para compras)
    // fetch LAZY para no cargar todos los registros hasta que se necesiten
    // orphanRemoval=true elimina registros de la relación si se quitan de este Set
    @OneToMany(mappedBy = "articulo", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ArticuloCompra> articulosCompra = new HashSet<>();

    // Constructor vacío 
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

    // Métodos para mantener la relación bidireccional sincronizada
    public void addArticuloCompra(ArticuloCompra ac) {
        this.articulosCompra.add(ac);
        ac.setArticulo(this); 
    }

    // Método para eliminar una relación ArticuloCompra
    public void removeArticuloCompra(ArticuloCompra ac) {
        this.articulosCompra.remove(ac);
        ac.setArticulo(null); // limpia la referencia para evitar inconsistencias
    }

    // equals y hashCode basados  en el ID 
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

    // toString 
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