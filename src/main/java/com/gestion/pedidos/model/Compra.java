package com.gestion.pedidos.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDate fechaCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCompra estado;

    @Column(name = "direccion_envio")
    private String direccionEnvio;

    @Column(name = "precio_total")
    private BigDecimal precioTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "compra", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticuloCompra> articulosCompra = new HashSet<>();

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

    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Set<ArticuloCompra> getArticulosCompra() { return articulosCompra; }
    public void setArticulosCompra(Set<ArticuloCompra> articulosCompra) { this.articulosCompra = articulosCompra; }

    // Métodos auxiliares para mantener relación bidireccional
    public void addArticuloCompra(ArticuloCompra ac) {
        articulosCompra.add(ac);
        ac.setCompra(this);
    }

    public void removeArticuloCompra(ArticuloCompra ac) {
        articulosCompra.remove(ac);
        ac.setCompra(null);
    }

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

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", fechaCompra=" + fechaCompra +
                ", estado=" + estado +
                ", direccionEnvio='" + direccionEnvio + '\'' +
                ", precioTotal=" + precioTotal +
                ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
                ", articulos=" + articulosCompra.size() +
                '}';
    }
}