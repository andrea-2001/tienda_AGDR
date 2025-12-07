package com.gestion.pedidos.model;

import java.time.LocalDate;
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
@Table(name = "cliente")
public class Cliente {

    // Clave primaria autogenerada
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Nombre del cliente, no nulo, máximo 100 caracteres
    @Column(nullable = false, length = 100)
    private String nombre;

    // Email único del cliente, no nulo, máximo 120 caracteres
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    // Fecha de registro del cliente, no nula
    @Column(name = "fecha_regist", nullable = false)
    private LocalDate fechaRegistro;

    // Relación 1:1 con InformacionFiscal
    @OneToOne(fetch = FetchType.LAZY, cascade = jakarta.persistence.CascadeType.ALL)
    @JoinColumn(name = "nif_cif", referencedColumnName = "nif_cif")
    private InformacionFiscal informacionFiscal;

    // Campo nif_cif como lectura (evita columna duplicada)
    @Column(name = "nif_cif", insertable = false, updatable = false)
    private String nifCif;

    // Constructor vacío
    public Cliente() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public InformacionFiscal getInformacionFiscal() { return informacionFiscal; }
    public void setInformacionFiscal(InformacionFiscal info) {
        if (info != null) {
            info.setCliente(this); // asegura la relación bidireccional
        }
        this.informacionFiscal = info;
    }

    public String getNifCif() { return nifCif; }
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    // equals y hashCode basados en ID
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
                ", fechaRegistro=" + fechaRegistro +
                ", nifCif='" + nifCif + '\'' +
                ", informacionFiscal=" + (informacionFiscal != null ? informacionFiscal.getNifCif() : "null") +
                '}';
    }
}