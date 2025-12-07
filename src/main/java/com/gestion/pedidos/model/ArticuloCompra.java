package com.gestion.pedidos.model;

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

    @Embeddable
    public static class ArticuloCompraPK implements Serializable {
        @Column(name = "articulo_id")
        private int articuloId;

        @Column(name = "compra_id")
        private int compraId;

        public ArticuloCompraPK() {}

        public ArticuloCompraPK(int articuloId, int compraId) {
            this.articuloId = articuloId;
            this.compraId = compraId;
        }

        public int getArticuloId() { return articuloId; }
        public void setArticuloId(int articuloId) { this.articuloId = articuloId; }

        public int getCompraId() { return compraId; }
        public void setCompraId(int compraId) { this.compraId = compraId; }

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

    @EmbeddedId
    private ArticuloCompraPK id = new ArticuloCompraPK();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articuloId")
    @JoinColumn(name = "articulo_id", nullable = false)
    private Articulo articulo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")
    @JoinColumn(name = "compra_id", nullable = false)
    private Compra compra;

    @Column(name = "unidades", nullable = false)
    private int unidades;

    @Column(name = "precio_compra", nullable = false)
    private double precioCompra;

    public ArticuloCompra() {}

    // Constructor simplificado
    public ArticuloCompra(Articulo articulo, Compra compra, int unidades, double precioCompra) {
        setArticulo(articulo);
        setCompra(compra);
        this.unidades = unidades;
        this.precioCompra = precioCompra;
    }

    public ArticuloCompraPK getId() { return id; }
    public void setId(ArticuloCompraPK id) { this.id = id; }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        if (articulo != null && id != null) {
            this.id.setArticuloId(articulo.getId());
        }
    }

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) {
        this.compra = compra;
        if (compra != null && id != null) {
            this.id.setCompraId(compra.getId());
        }
    }

    public int getUnidades() { return unidades; }
    public void setUnidades(int unidades) { this.unidades = unidades; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

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
}