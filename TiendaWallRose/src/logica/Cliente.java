package logica;

import java.util.Map;
import java.util.TreeMap;

public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private Map<Integer, OrdenCompra> ordenes;

    public Cliente(int id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        ordenes = new TreeMap<Integer, OrdenCompra>();
    }

    public boolean validarEmail(String email) {
        if (email != null) {
            if (email.contains("@")) {
                if (email.contains(".")) {
                    return true;
                }
            }
        }
        return false;
    }

    public int totalPendientes() {
        int contador = 0;
        for (OrdenCompra orden : ordenes.values()) {
            String estado = orden.getEstado();
            if (estado.equalsIgnoreCase("Pendiente")) {
                contador = contador + 1;
            }
        }
        return contador;
    }

    public void agregarOrden(OrdenCompra orden) {
        if (orden != null) {
            int numero = orden.getNumero();
            ordenes.put(numero, orden);
        }
    }

    public void borrarOrden(int numeroOrden) throws Exception {
        if (ordenes.containsKey(numeroOrden)) {
            ordenes.remove(numeroOrden);
            return;
        }
        throw new Exception("Error: No se pudo borrar, la orden no existe en el registro.");
    }

    // GETTERS Y SETTERS

    public int getId() {
        return id;
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

    public void setEmail(String email) throws Exception {
        if (validarEmail(email)) {
            this.email = email;
            return;
        }
        throw new Exception("Error: El formato del email es incorrecto.");
    }

    public Map<Integer, OrdenCompra> getOrdenes() {
        return ordenes;
    }

    public String toString() {
        return "\n  DATOS DEL CLIENTE " +
               "\nID: " + id +
               "\nNombre: " + nombre +
               "\nEmail: " + email +
               "\nTotal de Órdenes: " + ordenes.size() +
               "\nÓrdenes Pendientes: " + totalPendientes() +
               "\n----------------------------";
    }
}