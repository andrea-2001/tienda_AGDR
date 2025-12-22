package com.gestion.pedidos.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Representa la informacion fiscal de un cliente en el sistema.
 * Mantiene la relación bidireccional con Cliente.
 * @author Andrea
 * @since 2025-12-15
 * @version 1.0
 */
@Entity
@Table(name = "informacion_fiscal")
public class InformacionFiscal {

	// NIF/CIF como clave primaria de la tabla
    @Id
    @Column(name = "nif_cif", length = 20)
    private String nifCif;

    // Teléfono del cliente 
    @Column(name = "telefono")
    private String telefono;

    // Dirección fiscal del cliente 
    @Column(name = "direccion_fiscal")
    private String direccionFiscal;

    // Relación 1:1 bidireccional con Cliente, mappedBy indica que Cliente es el dueño
    @OneToOne(mappedBy = "informacionFiscal", fetch = FetchType.LAZY) // LAZY para no cargar automáticamente
    private Cliente cliente;

    // Constructor 
    public InformacionFiscal() {}

    // Getters y Setters
    public String getNifCif() { return nifCif; }
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccionFiscal() { return direccionFiscal; }
    public void setDireccionFiscal(String direccionFiscal) { this.direccionFiscal = direccionFiscal; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    // Métodos equals y hashCode basados en el NIF/CIF
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InformacionFiscal)) return false;
        InformacionFiscal that = (InformacionFiscal) o;
        return Objects.equals(nifCif, that.nifCif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nifCif);
    }

    // toString 
    @Override
    public String toString() {
        return "InformacionFiscal{" +
                "nifCif='" + nifCif + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccionFiscal='" + direccionFiscal + '\'' +
                ", clienteId=" + (cliente != null ? cliente.getId() : "null") +
                '}';
    }
}