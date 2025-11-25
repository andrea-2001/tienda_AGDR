package com.gestion.pedidos;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ArticuloCompraId implements Serializable {

    // Componentes de la clave compuesta (los IDs de las entidades relacionadas)
    @Column(name = "articulo_id")
    private int articuloId;

    @Column(name = "compra_id")
    private int compraId;

    public ArticuloCompraId() {}

    public ArticuloCompraId(int articuloId, int compraId) {
        this.articuloId = articuloId;
        this.compraId = compraId;
    }
    
    // Getters y Setters
    
    public int getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }
    
    // metodos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticuloCompraId that = (ArticuloCompraId) o;
        return articuloId == that.articuloId &&
               compraId == that.compraId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(articuloId, compraId);
    }
}