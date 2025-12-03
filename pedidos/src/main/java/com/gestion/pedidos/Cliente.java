package com.gestion.pedidos;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

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

    // NIF o CIF del cliente, obligatorio, máximo 20 caracteres
    @Column(name = "nif_cif", nullable = false, length = 20)
    private String nifCif;

    // Fecha de registro del cliente, no nula
    @Column(name = "fecha_regist", nullable = false)
    private LocalDate fechaRegistro;

    // Relación 1:1 con InformacionFiscal
    // fetch LAZY para no cargar información fiscal hasta que se necesite
    // insertable = false, updatable = false la relación se gestiona desde InformacionFiscal
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nif_cif", referencedColumnName = "nif_cif", insertable = false, updatable = false)
    private InformacionFiscal informacionFiscal;

    // Constructor vacío 
    public Cliente() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNifCif() { return nifCif; }
    public void setNifCif(String nifCif) { this.nifCif = nifCif; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public InformacionFiscal getInformacionFiscal() { return informacionFiscal; }

    // Mantiene la relación bidireccional con InformacionFiscal
    public void setInformacionFiscal(InformacionFiscal info) {
        if (info == null) {
            if (this.informacionFiscal != null)
                this.informacionFiscal.setCliente(null); 
        } else {
            info.setCliente(this); // asigna el cliente a la información fiscal
        }
        this.informacionFiscal = info;
    }

    // equals basado en ID 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente c = (Cliente) o;

        // Si ambos objetos no tienen ID asignado, usa equals por defecto
        if (id == 0 && c.id == 0)
            return super.equals(o);

        return id == c.id;
    }

    // hashCode basado en ID o identidad si no tiene ID
    @Override
    public int hashCode() {
        return id != 0 ? Objects.hash(id) : System.identityHashCode(this);
    }

    // toString 
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