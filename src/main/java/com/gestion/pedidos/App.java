package com.gestion.pedidos;

import com.gestion.pedidos.model.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.math.BigDecimal;

public class App {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("gestion-pedidos-pu");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // -------------------------
            // 1) Crear/obtener cliente
            // -------------------------
            Cliente clienteCompleto = new Cliente();
            clienteCompleto.setNombre("Juan Pérez");
            clienteCompleto.setEmail("juan.perez@email.com");
            clienteCompleto.setFechaRegistro(LocalDate.now());

            // Crear Info Fiscal
            InformacionFiscal infoFiscal = new InformacionFiscal();
            infoFiscal.setNifCif("NIF_" + System.currentTimeMillis());
            infoFiscal.setTelefono("600123456");
            infoFiscal.setDireccionFiscal("Calle Falsa 123");

            // Asociar bidireccionalmente
            clienteCompleto.setInformacionFiscal(infoFiscal);

            // Persistir cliente (cascada persiste infoFiscal)
            em.persist(clienteCompleto);

            // -------------------------
            // 2) Crear artículo
            // -------------------------
            Articulo articulo = new Articulo();
            articulo.setNombre("Camiseta");
            articulo.setDescripcion("Camiseta algodón talla M");
            articulo.setPrecio(19.95);
            articulo.setStock(100);
            em.persist(articulo);

            // -------------------------
            // 3) Crear compra
            // -------------------------
            Compra compraNueva = new Compra();
            compraNueva.setCliente(clienteCompleto);
            compraNueva.setFechaCompra(LocalDate.now());
            compraNueva.setDireccionEnvio("Calle Falsa 123");
            compraNueva.setPrecioTotal(BigDecimal.valueOf(59.85));
            compraNueva.setEstado(EstadoCompra.PENDIENTE);

            em.persist(compraNueva); // ID generado por JPA

            // IMPORTANTE: Flush para asegurar que las entidades tienen ID
            em.flush();
            em.clear(); // Opcional: limpia el contexto de persistencia

            // Re-cargar entidades para asegurar que están en estado managed
            articulo = em.find(Articulo.class, articulo.getId());
            compraNueva = em.find(Compra.class, compraNueva.getId());

            // -------------------------
            // 4) Crear ArticuloCompra
            // -------------------------
            ArticuloCompra ac = new ArticuloCompra();
            ac.setArticulo(articulo);
            ac.setCompra(compraNueva);
            ac.setUnidades(3);
            ac.setPrecioCompra(19.95);

            // Verificar que los IDs están correctamente asignados
            System.out.println("Articulo ID: " + articulo.getId());
            System.out.println("Compra ID: " + compraNueva.getId());
            System.out.println("ArticuloCompra ID: " + ac.getId().getArticuloId() + ", " + ac.getId().getCompraId());

            // Usar métodos auxiliares para mantener relaciones bidireccionales
            compraNueva.addArticuloCompra(ac);
            articulo.addArticuloCompra(ac);

            em.persist(ac);

            tx.commit();

            System.out.println("Transacción completada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            if (tx.isActive()) tx.rollback();
            System.err.println("ERROR DURANTE LA TRANSACCIÓN");
        } finally {
            em.close();
            emf.close();
        }
    }
}