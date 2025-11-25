package com.gestion.pedidos;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "articulos")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id; 

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre; 

    @Column(name = "descripcion", length = 500)
    private String descripcion; 

    @Column(name = "precio", nullable = false)
    private double precio; 

    @Column(name = "stock", nullable = false)
    private int stock; 

    // Relación Uno a Muchos (1:N) con la tabla de unión ArticuloCompra
    @OneToMany(mappedBy = "articulo")
    private Set<ArticuloCompra> articulosCompra = new HashSet<>();


    public Articulo() {}

    // Getters y Setters
    

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



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public double getPrecio() {
		return precio;
	}



	public void setPrecio(double precio) {
		this.precio = precio;
	}



	public int getStock() {
		return stock;
	}



	public void setStock(int stock) {
		this.stock = stock;
	}


	public Set<ArticuloCompra> getArticulosCompra() {
        return articulosCompra;
    }

    public void setArticulosCompra(Set<ArticuloCompra> articulosCompra) {
        this.articulosCompra = articulosCompra;
    }
    
    public void addArticuloCompra(ArticuloCompra ac) {
        this.articulosCompra.add(ac);
        ac.setArticulo(this);
    }

    public void removeArticuloCompra(ArticuloCompra ac) {
        this.articulosCompra.remove(ac);
        ac.setArticulo(null);
    }
}