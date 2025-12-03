package com.gestion.pedidos;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulo_compra")
public class ArticuloCompra {

	 // Clase interna para la clave primaria compuesta (articulo_id + compra_id)
    @Embeddable
    public static class ArticuloCompraPK implements Serializable {

        // ID del artículo
        @Column(name = "articulo_id")
        private int articuloId;

        // ID de la compra
        @Column(name = "compra_id")
        private int compraId;

        // Constructores
        public ArticuloCompraPK() {}

        
        public ArticuloCompraPK(int articuloId, int compraId) {
            this.articuloId = articuloId;
            this.compraId = compraId;
        }

        // Getters y setters
        public int getArticuloId() { return articuloId; }
        public void setArticuloId(int articuloId) { this.articuloId = articuloId; }

        public int getCompraId() { return compraId; }
        public void setCompraId(int compraId) { this.compraId = compraId; }

        // equals y hashCode basados en los IDs
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ArticuloCompraPK)) return false;
            ArticuloCompraPK that = (ArticuloCompraPK) o;
            return articuloId == that.articuloId && compraId == that.compraId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(articuloId, compraId);
        }
    }

    // Clave primaria compuesta embebida
    @EmbeddedId
    private ArticuloCompraPK id = new ArticuloCompraPK();

    // Relación ManyToOne con Articulo, usa MapsId para sincronizar con la clave compuesta
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articuloId")  // indica que este campo corresponde al articuloId de la PK
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    // Relación ManyToOne con Compra, sincronizada con compraId de la PK
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")   // indica que este campo corresponde al compraId de la PK
    @JoinColumn(name = "compra_id")
    private Compra compra;

    // Cantidad de unidades compradas
    @Column(name = "unidades", nullable = false)
    private int unidades;

    // Precio de compra del artículo
    @Column(name = "precio_compra", nullable = false)
    private double precioCompra;

    // Constructores
    public ArticuloCompra() {}

  
    public ArticuloCompra(Articulo articulo, Compra compra, int unidades, double precioCompra) {
        this.articulo = articulo;
        this.compra = compra;
        this.unidades = unidades;
        this.precioCompra = precioCompra;

        // Sincroniza la clave primaria compuesta con los IDs
        this.id.setArticuloId(articulo.getId());
        this.id.setCompraId(compra.getId());
    }

    // Getters y Setters
    public ArticuloCompraPK getId() { return id; }
    public void setId(ArticuloCompraPK id) { this.id = id; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        if (articulo != null) this.id.setArticuloId(articulo.getId());
    }

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) {
        this.compra = compra;
        if (compra != null) this.id.setCompraId(compra.getId());
    }

    public int getUnidades() { return unidades; }
    public void setUnidades(int unidades) { this.unidades = unidades; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    // equals y hashCode 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticuloCompra)) return false;
        ArticuloCompra that = (ArticuloCompra) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString 
    @Override
    public String toString() {
        return "ArticuloCompra{" +
                "articuloId=" + (articulo != null ? articulo.getId() : "null") +
                ", compraId=" + (compra != null ? compra.getId() : "null") +
                ", unidades=" + unidades +
                ", precioCompra=" + precioCompra +
                '}';
    }
}