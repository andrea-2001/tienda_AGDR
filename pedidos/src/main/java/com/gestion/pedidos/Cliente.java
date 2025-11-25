package com.gestion.pedidos;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "nif_cif", nullable = false, unique = true, length = 20)
    private String nifCif;

    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // relación
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY, orphanRemoval = true)
    private InformacionFiscal informacionFiscal;

    public Cliente() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNifCif() {
        return nifCif;
    }

    public void setNifCif(String nifCif) {
        this.nifCif = nifCif;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public InformacionFiscal getInformacionFiscal() {
        return informacionFiscal;
    }

    public void setInformacionFiscal(InformacionFiscal info) {
        if (info == null) {
            if (this.informacionFiscal != null)
                this.informacionFiscal.setCliente(null);
        } else {
            info.setCliente(this);
        }
        this.informacionFiscal = info;
    }

    //metodos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;

        Cliente c = (Cliente) o;

        if (id == 0 && c.id == 0)
            return super.equals(o);

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
                ", infoFiscal_id=" + (informacionFiscal != null ? informacionFiscal.getId() : "N/A") +
                '}';
    }
}