package com.gestion.pedidos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

public class App {

    public static void main(String[] args) {
        // Configurar el EntityManagerFactory
        // Carga la configuración del persistence.xml
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            emf = Persistence.createEntityManagerFactory("gestion-pedidos-pu");
            em = emf.createEntityManager();

            // ----------------------------------------------------
            // LEER DATOS EXISTENTES (Usando LAZY loading)
            // ----------------------------------------------------
            System.out.println("--- LEYENDO DATOS EXISTENTES ---");

            // A) Consultar Cliente y su Información Fiscal (1:1)
            // La información fiscal es LAZY. No se carga hasta que no hacemos .getInformacionFiscal()
            System.out.println("\n[CONSULTA] Buscando cliente con ID 1...");
            Cliente cliente = em.find(Cliente.class, 1);
            if (cliente != null) {
                System.out.println("-> Cliente encontrado: " + cliente.getNombre());
                if (cliente.getInformacionFiscal() != null) {
                    System.out.println("-> Información Fiscal (LAZY 1:1): " + 
                                     cliente.getInformacionFiscal().getDireccionFiscal());
                } else {
                    System.out.println("-> El cliente no tiene información fiscal asociada.");
                }
            }

            // B) Consultar Cliente y sus Compras (1:N)
            // Las compras son LAZY. No se cargan hasta que no accedemos a través de consultas
            System.out.println("\n[CONSULTA] Buscando compras del cliente...");
            if (cliente != null) {
                String jpql = "SELECT c FROM Compra c WHERE c.cliente = :cliente";
                List<Compra> comprasList = em.createQuery(jpql, Compra.class)
                                           .setParameter("cliente", cliente)
                                           .getResultList();
                Set<Compra> comprasDelCliente = new HashSet<>(comprasList);
                System.out.println("-> '" + cliente.getNombre() + "' ha realizado " + comprasDelCliente.size() + " compras.");
                for (Compra compra : comprasDelCliente) {
                    System.out.println("   - Compra ID: " + compra.getId() + ", Estado: " + compra.getEstado());
                }
            }

            // C) Consultar Compra, sus Artículos (N:M a través de ArticuloCompra)
            System.out.println("\n[CONSULTA] Buscando Compra ID 1...");
            Compra compra = em.find(Compra.class, 1);
            if (compra != null) {
                System.out.println("-> Compra encontrada: ID " + compra.getId());
                System.out.println("-> Cliente (LAZY N:1): " + compra.getCliente().getNombre());
                System.out.println("-> Artículos en la compra (LAZY N:M):");
                for (ArticuloCompra ac : compra.getArticulosCompra()) {
                    System.out.println("   - " + ac.getArticulo().getNombre() + 
                                     " (Unidades: " + ac.getUnidades() + 
                                     ", Precio: " + ac.getPrecioCompra() + ")");
                }
            }

            // ----------------------------------------------------
            // CREAR DATOS NUEVOS
            // ----------------------------------------------------
            System.out.println("\n--- CREANDO DATOS NUEVOS (usando una transacción) ---");
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            try {
                // Crear un cliente SIN información fiscal
                Cliente clienteSimple = new Cliente();
                clienteSimple.setNombre("cliente_temporal_" + System.currentTimeMillis());
                clienteSimple.setEmail("temp_" + System.currentTimeMillis() + "@ejemplo.com");
                clienteSimple.setNifCif("TEMP_" + System.currentTimeMillis());

                em.persist(clienteSimple);
                System.out.println("\n[CREANDO] " + clienteSimple.getNombre());

                // Crear un cliente CON información fiscal
                Cliente clienteCompleto = new Cliente();
                clienteCompleto.setNombre("cliente_completo_temp");
                clienteCompleto.setEmail("completo@ejemplo.com");
                clienteCompleto.setNifCif("COMPLETO_TEMP");

                InformacionFiscal infoFiscal = new InformacionFiscal();
                infoFiscal.setTelefono("+34 600 000 000");
                infoFiscal.setDireccionFiscal("Calle Temporal, 123, Madrid");

                // Usamos el helper para sincronizar la 1:1
                clienteCompleto.setInformacionFiscal(infoFiscal);

                System.out.println("[CREANDO] " + clienteCompleto.getNombre() + " con su información fiscal.");

                // Crear un artículo
                Articulo articuloNuevo = new Articulo();
                articuloNuevo.setNombre("Artículo Temporal " + System.currentTimeMillis());
                articuloNuevo.setDescripcion("Descripción del artículo temporal");
                articuloNuevo.setPrecio(29.99);
                articuloNuevo.setStock(100);

                // Crear una compra
                Compra compraNueva = new Compra();
                compraNueva.setCliente(clienteCompleto);
                compraNueva.setDireccion("Dirección de envío temporal");
                compraNueva.setEnvio(5.99);
                compraNueva.setPrecioTotal(35.98);
                compraNueva.setEstado(EstadoCompra.PENDIENTE);

                // Crear la relación ArticuloCompra
                ArticuloCompra articuloCompra = new ArticuloCompra();
                articuloCompra.setArticulo(articuloNuevo);
                articuloCompra.setCompra(compraNueva);
                articuloCompra.setUnidades(2);
                articuloCompra.setPrecioCompra(29.99);

                // Usar los métodos helper para mantener la sincronización bidireccional
                compraNueva.addArticuloCompra(articuloCompra);
                articuloNuevo.addArticuloCompra(articuloCompra);

                System.out.println("[CREANDO] Artículo '" + articuloNuevo.getNombre() + "'");
                System.out.println("[CREANDO] Compra para cliente '" + clienteCompleto.getNombre() + "'");
                System.out.println("[ASIGNANDO] " + articuloCompra.getUnidades() + " unidades del artículo a la compra");

                // Persistir los objetos nuevos
                em.persist(clienteCompleto);
                em.persist(articuloNuevo);
                em.persist(compraNueva);
                em.persist(articuloCompra);

                System.out.println("\n[FLUSH] Forzando SQL INSERTs (pero sin commit)...");
                // Forzamos que Hibernate envíe el SQL a la BBDD
                em.flush();

                System.out.println("\n[COMMIT] Guardando todos los cambios...");
                tx.commit();
                System.out.println("¡Todos los cambios guardados correctamente!");

            } catch (PersistenceException e) {
                System.err.println("!!! ERROR EN LA TRANSACCIÓN !!!");
                e.printStackTrace();
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                    System.out.println("¡Rollback completado! La base de datos está intacta.");
                }
            }

        } catch (Exception e) {
            System.err.println("!!! ERROR AL INICIAR JPA !!!");
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
            System.out.println("\n--- APLICACIÓN FINALIZADA ---");
        }
    }
}
