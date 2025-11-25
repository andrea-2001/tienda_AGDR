package com.gestion.pedidos;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @CreationTimestamp
    @Column(name = "fecha_compra", nullable = false, updatable = false)
    private LocalDateTime fechaCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoCompra estado;

    @Column(name = "direccion_envio", nullable = false, length = 500)
    private String direccion;

    @Column(name = "costo_envio", nullable = false)
    private double envio;

    @Column(name = "precio_total", nullable = false)
    private double precioTotal;

    // relación 1:n con cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
 // RELACIÓN N:N (Mapeada a través de la tabla de unión ArticuloCompra)
    // Se mapea como una relación Uno a Muchos (1:N) con la entidad ArticuloCompra
    @OneToMany(mappedBy = "compra")
    private Set<ArticuloCompra> articulosCompra = new HashSet<>();

    public Compra() {
        this.estado = EstadoCompra.PENDIENTE;
    }

 // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getEnvio() {
        return envio;
    }

    public void setEnvio(double envio) {
        this.envio = envio;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ArticuloCompra> getArticulosCompra() {
        return articulosCompra;
    }

    public void setArticulosCompra(Set<ArticuloCompra> articulosCompra) {
        this.articulosCompra = articulosCompra;
    }

 // Ayudan a mantener la relación bidireccional ArticuloCompra <-> Compra sincronizada

    public void addArticuloCompra(ArticuloCompra ac) {
        this.articulosCompra.add(ac);
        // Asegura que el lado "compra" de la relación también esté configurado
        ac.setCompra(this); 
    }

    public void removeArticuloCompra(ArticuloCompra ac) {
        this.articulosCompra.remove(ac);
        // Quita la referencia
        ac.setCompra(null); 
    }

    // metodos
	@Override
	public int hashCode() {
		return Objects.hash(cliente, direccion, envio, estado, fechaCompra, id, precioTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(direccion, other.direccion)
				&& Double.doubleToLongBits(envio) == Double.doubleToLongBits(other.envio) && estado == other.estado
				&& Objects.equals(fechaCompra, other.fechaCompra) && id == other.id
				&& Double.doubleToLongBits(precioTotal) == Double.doubleToLongBits(other.precioTotal);
	}

	@Override
	public String toString() {
		return "Compra [id=" + id + ", fechaCompra=" + fechaCompra + ", estado=" + estado + ", direccion=" + direccion
				+ ", envio=" + envio + ", precioTotal=" + precioTotal + ", cliente=" + cliente + "]";
	}
    
    
}