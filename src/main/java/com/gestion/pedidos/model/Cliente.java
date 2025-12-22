package com.gestion.pedidos.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


/**
 * Representa un cliente en el sistema.
 * @author Andrea
 * @since 2025-12-15
 * @version 1.0
 */

@Entity
@Table(name = "cliente")
public class Cliente { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "nif_cif", nullable = false, length = 20, insertable = false, updatable = false)
    private String nifCif;

    @Column(name = "fecha_regist", nullable = false)
    private LocalDate fechaRegistro;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "nif_cif", referencedColumnName = "nif_cif")
    private InformacionFiscal informacionFiscal;

    public Cliente() {
        this.fechaRegistro = LocalDate.now();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNifCif() { return nifCif; }

    public void setNifCif(String nifCif) {
        if (this.informacionFiscal != null) {
            this.informacionFiscal.setNifCif(nifCif);
        }

        this.nifCif = nifCif;
    }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public InformacionFiscal getInformacionFiscal() { return informacionFiscal; }

    public void setInformacionFiscal(InformacionFiscal info) {
        if (info == null) {
            if (this.informacionFiscal != null)
                this.informacionFiscal.setCliente(null);
        } else {
            info.setCliente(this);
            if (this.nifCif != null) {
                info.setNifCif(this.nifCif);
            }
        }
        this.informacionFiscal = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente c = (Cliente) o;
        if (id == 0 && c.id == 0) return super.equals(o);
        return id == c.id;
    }

    @Override
    public int hashCode() {
        return id != 0 ? Objects.hash(id) : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", nifCif='" + nifCif + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", informacionFiscalNif='" + (informacionFiscal != null ? informacionFiscal.getNifCif() : "null") +
                "'}";
    }
}