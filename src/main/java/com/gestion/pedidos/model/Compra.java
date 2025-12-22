package com.gestion.pedidos.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.math.BigDecimal;
import jakarta.persistence.*;

/**
 * Representa una compra en el sistema.
 * @author Andrea
 * @since 2025-12-15
 * @version 1.0
 */

@Entity
@Table(name = "compras")
public class Compra {

	 // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Fecha de la compra, no puede ser nula
    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    // Estado de la compra
    @Enumerated(EnumType.STRING) //se guarda el estado como cadena en la bd
    @Column(name = "estado", nullable = false)
    private EstadoCompra estado;

    // Dirección de envío de la compra
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    // Precio total de la compra
    @Column(name = "precio_total")
    private Double precioTotal;

    // Relación N:1 con Cliente (muchas compras para un cliente)
    @ManyToOne(fetch = FetchType.LAZY) // fetch LAZY para cargar solo cuando se necesite
    @JoinColumn(name = "cliente_id") // fk de cliente
    private Cliente cliente;

    // Relación 1:N con ArticuloCompra (una compra tiene muchos artículos)
    @OneToMany(mappedBy = "compra")
    private Set<ArticuloCompra> articulosCompra = new HashSet<>(); //colecion de artículos en la compra

    // Constructor: por defecto
    public Compra() {
        this.estado = EstadoCompra.PENDIENTE;
        this.fechaCompra = LocalDate.now(); // fecha por defecto para evitar errores de no-null
    }

    // Getters y Setters
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }

    public EstadoCompra getEstado() { return estado; }
    public void setEstado(EstadoCompra estado) { this.estado = estado; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public Double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(Double precioTotal) { this.precioTotal = precioTotal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Set<ArticuloCompra> getArticulosCompra() { return articulosCompra; }
    public void setArticulosCompra(Set<ArticuloCompra> articulosCompra) { this.articulosCompra = articulosCompra; }

    // Métodos auxiliares para mantener la relación bidireccional
    //añade un articulo a la compra
    public void addArticuloCompra(ArticuloCompra ac) {
        articulosCompra.add(ac);
        ac.setCompra(this); 
    }
    //elimina un articulo de la compra
    public void removeArticuloCompra(ArticuloCompra ac) {
        articulosCompra.remove(ac);
        ac.setCompra(null); // rompe la relación
    }

    // equals y hashCode basados en ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compra)) return false;
        Compra compra = (Compra) o;
        return id != 0 && id == compra.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString 
    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", fechaCompra=" + fechaCompra +
                ", estado=" + estado +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", precioTotal=" + precioTotal +
                ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
                ", articulos=" + articulosCompra.size() + // solo muestra la cantidad de artículos
                '}';
    }
}