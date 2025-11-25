package com.gestion.pedidos;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "articulo_compra")
public class ArticuloCompra {

    // Mapea la clave primaria
    @EmbeddedId
    private ArticuloCompraId id;

    // Atributos 
    @Column(name = "unidades", nullable = false)
    private int unidades; 

    @Column(name = "precio_compra", nullable = false)
    private double precioCompra; 

    //  Mapea de las Claves Foráneas (N:1) 
    // @MapsId enlaza el ManyToOne con el componente de la clave compuesta
    @ManyToOne
    @MapsId("articuloId") // Usa el campo 'articuloId' de ArticuloCompraId
    @JoinColumn(name = "articulo_id")
    private Articulo articulo; // obtiene el objeto Articulo completo

    @ManyToOne
    @MapsId("compraId") // Usa el campo 'compraId' de ArticuloCompraId
    @JoinColumn(name = "compra_id")
    private Compra compra; // obtiene el objeto Compra completo


    public ArticuloCompra() {
        this.id = new ArticuloCompraId();
    }
    
    public ArticuloCompra(Articulo articulo, Compra compra, int unidades, double precioCompra) {
        this.articulo = articulo;
        this.compra = compra;
        this.unidades = unidades;
        this.precioCompra = precioCompra;
        
        // Inicializa la clave compuesta con los IDs de las entidades
        this.id = new ArticuloCompraId(articulo.getId(), compra.getId());
    }
    
    // Getters y Setters
    
    public ArticuloCompraId getId() {
        return id;
    }

    public void setId(ArticuloCompraId id) {
        this.id = id;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
        if (articulo != null && this.id != null) {
            this.id.setArticuloId(articulo.getId());
        }
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
        if (compra != null && this.id != null) {
            this.id.setCompraId(compra.getId());
        }
    }
}