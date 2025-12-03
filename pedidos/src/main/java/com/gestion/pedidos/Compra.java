package com.gestion.pedidos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;

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
    @Enumerated(EnumType.STRING)
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
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relación 1:N con ArticuloCompra (una compra tiene muchos artículos)
    @OneToMany(mappedBy = "compra")
    private Set<ArticuloCompra> articulosCompra = new HashSet<>();

    // Constructor: por defecto
    public Compra() {
        this.estado = EstadoCompra.PENDIENTE;
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
    public void addArticuloCompra(ArticuloCompra ac) {
        articulosCompra.add(ac);
        ac.setCompra(this); 
    }

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