package com.gestion.pedidos;

import java.util.List;
import java.time.LocalDate;

import com.gestion.pedidos.model.Articulo;
import com.gestion.pedidos.model.ArticuloCompra;
import com.gestion.pedidos.model.Cliente;
import com.gestion.pedidos.model.Compra;
import com.gestion.pedidos.model.EstadoCompra;
import com.gestion.pedidos.model.InformacionFiscal;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * Gestión de Pedidos app principal.
 * @author Daniel
 * @since 2025-12-15
 * @version 1.0
 */
public class App {

    public static void main(String[] args) {
    	  EntityManagerFactory emf = null;
    	    EntityManager em = null;

    	    try {
    	        emf = Persistence.createEntityManagerFactory("gestion-pedidos-pu");
    	        em = emf.createEntityManager();

    	        System.out.println("\n============= LECTURAS =============");

    	        // A) Leer Cliente + Información Fiscal (1:1)
    	        System.out.println("\n[CONSULTA] Cliente ID = 1");

    	        Cliente cliente = em.find(Cliente.class, 1);
    	        if (cliente != null) {
    	            System.out.println("Cliente: " + cliente.getNombre());

    	            if (cliente.getInformacionFiscal() != null) {
    	                System.out.println("Info Fiscal (LAZY): " +
    	                        cliente.getInformacionFiscal().getDireccionFiscal());
    	            } else {
    	                System.out.println("Este cliente NO tiene información fiscal.");
    	            }
    	        }

    	        // B) Compras de un cliente (1:N)
    	        if (cliente != null) {
    	            System.out.println("\n[CONSULTA] Compras del cliente " + cliente.getNombre());

    	            String jpql = "SELECT c FROM Compra c WHERE c.cliente = :cli";
    	            List<Compra> compras = em.createQuery(jpql, Compra.class)
    	                                     .setParameter("cli", cliente)
    	                                     .getResultList();

    	            System.out.println("Número de compras: " + compras.size());
    	            for (Compra c : compras) {
    	                System.out.println(" - Compra ID: " + c.getId() + " Estado=" + c.getEstado());
    	            }
    	        }

    	        // C) Artículos de una compra (N:N)
    	        System.out.println("\n[CONSULTA] Compra ID = 1");

    	        Compra compra = em.find(Compra.class, 1);
    	        if (compra != null) {
    	            System.out.println("Compra encontrada. Cliente = " + compra.getCliente().getNombre());
    	            System.out.println("Artículos asociados:");

    	            for (ArticuloCompra ac : compra.getArticulosCompra()) {
    	                System.out.println(" - " + ac.getArticulo().getNombre() +
    	                        " | unidades=" + ac.getUnidades() +
    	                        " | precio=" + ac.getPrecioCompra());
    	            }
    	        }


    	        // CREACIÓN DE NUEVOS DATOS
    	        System.out.println("\n============= CREANDO NUEVOS DATOS =============");

    	        EntityTransaction tx = em.getTransaction();
    	        tx.begin();

    	        try {

    	            // 1) Crea CLIENTE 
    	            Cliente clienteSimple = new Cliente();
    	            clienteSimple.setNombre("ClienteSimple_" + System.currentTimeMillis());
    	            clienteSimple.setEmail("simple@ejemplo.com");
    	            clienteSimple.setNifCif("NIF_" + System.currentTimeMillis());

    	            InformacionFiscal infoSimple = new InformacionFiscal();
    	            infoSimple.setNifCif(clienteSimple.getNifCif());
    	            clienteSimple.setInformacionFiscal(infoSimple);

    	            em.persist(clienteSimple);

    	            System.out.println("[CREADO] Cliente simple: " + clienteSimple.getNombre());


    	            // 2) Crea CLIENTE con información fiscal
    	            String nifCompleto = "COMPLETO_001";
    	            InformacionFiscal info = new InformacionFiscal();
    	            info.setTelefono("600000123");
    	            info.setDireccionFiscal("Calle Fiscal, 123 Madrid");
    	            info.setNifCif(nifCompleto);

    	            Cliente clienteCompleto = new Cliente();
    	            clienteCompleto.setNombre("ClienteConFiscal");
    	            clienteCompleto.setEmail("completo@ejemplo.com");
    	            clienteCompleto.setNifCif(nifCompleto);
    	            clienteCompleto.setInformacionFiscal(info);

    	            em.persist(clienteCompleto);

    	            System.out.println("[CREADO] Cliente con información fiscal");


    	            // 3) Crea ARTÍCULO
    	            Articulo articulo = new Articulo();
    	            articulo.setNombre("Producto_" + System.currentTimeMillis());
    	            articulo.setDescripcion("Descripción temporal");
    	            articulo.setPrecio(19.95);
    	            articulo.setStock(50);

    	            em.persist(articulo);

    	            System.out.println("[CREADO] Artículo: " + articulo.getNombre());


    	            // 4) Crea COMPRA
    	            Compra compraNueva = new Compra();
    	            compraNueva.setCliente(clienteCompleto);
    	            compraNueva.setDireccionEnvio("Dirección temporal");
    	            compraNueva.setPrecioTotal(24.90);
    	            // fechaCompra is not nullable in the entity => set a value
    	            compraNueva.setFechaCompra(LocalDate.now());
    	            compraNueva.setEstado(EstadoCompra.PENDIENTE);

    	            em.persist(compraNueva);

    	            System.out.println("[CREADO] Compra para cliente: " + clienteCompleto.getNombre());


    	            // 5) RELACIÓN ArticuloCompra
    	            ArticuloCompra ac = new ArticuloCompra();
    	            ac.setArticulo(articulo);
    	            ac.setCompra(compraNueva);
    	            ac.setUnidades(3);
    	            ac.setPrecioCompra(19.95);

    	            // Mantener la relación bidireccional
    	            compraNueva.addArticuloCompra(ac);
    	            articulo.addArticuloCompra(ac);

    	            em.persist(ac);

    	            System.out.println("[ASIGNADO] Artículo " + articulo.getNombre() +
    	                    " x3 unidades a la compra.");


    	            // Finaliza transacción
    	            em.flush();
    	            tx.commit();

    	            System.out.println("\n ¡TODOS LOS DATOS SE GUARDARON CORRECTAMENTE!");

    	        } catch (Exception ex) {
    	            System.err.println("ERROR DURANTE LA TRANSACCIÓN");
    	            ex.printStackTrace();
    	            if (tx.isActive()) tx.rollback();
    	        }

    	    } catch (Exception e) {
    	        System.err.println("ERROR AL INICIAR JPA");
    	        e.printStackTrace();

    	    } finally {
    	        if (em != null) em.close();
    	        if (emf != null) emf.close();
    	        System.out.println("\n============= APLICACIÓN FINALIZADA =============");
    	    }

    	}
}