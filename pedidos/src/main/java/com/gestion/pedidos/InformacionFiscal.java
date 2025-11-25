package com.gestion.pedidos;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "informacion_fiscal")
public class InformacionFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @Column(name = "direccion_fiscal", nullable = false, length = 255)
    private String direccionFiscal;

    // relacion 1:1 con cliente
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false, unique = true)
    private Cliente cliente;

    public InformacionFiscal() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionFiscal() {
        return direccionFiscal;
    }

    public void setDireccionFiscal(String direccionFiscal) {
        this.direccionFiscal = direccionFiscal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // metodos

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformacionFiscal)) return false;

        InformacionFiscal info = (InformacionFiscal) o;

        if (id == 0 && info.id == 0)
            return super.equals(o);

        return id == info.id;
    }

    @Override
    public int hashCode() {
        return id != 0 ? Objects.hash(id) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "InformacionFiscal{" +
                "id=" + id +
                ", telefono='" + telefono + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", cliente_id=" + (cliente != null ? cliente.getId() : "N/A") +
                '}';
    }
}