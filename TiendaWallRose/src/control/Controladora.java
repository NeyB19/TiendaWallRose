package control;

import logica.Cliente;
import logica.Linea;
import logica.Producto;
import logica.OrdenCompra;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Controladora {
    private static Controladora instance = null;
    private Map<String, Cliente> clientes;
    private Map<Integer, Producto> productos;
    private Map<Integer, OrdenCompra> ordenes;

    private Controladora() {
        this.clientes = new TreeMap<String, Cliente>();
        this.productos = new TreeMap<Integer, Producto>();
        this.ordenes = new TreeMap<Integer, OrdenCompra>();
    }

    public static Controladora getInstance() {
        if (instance == null) {
            instance = new Controladora();
        }
        return instance;
    }

 // MÉTODOS DE CLIENTES

    public List<Cliente> obtenerListadoClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>(this.clientes.values()); 
        return listaClientes;
    }

    public Cliente obtenerCliente(String id) throws Exception {
        if (this.clientes.containsKey(id)) {
            return this.clientes.get(id);
        }
        throw new Exception("Error: El Cliente con esa ID no existe");
    }

    public void crearCliente(String id, String nombre, String email) throws Exception {
        if (!this.clientes.containsKey(id)) {
            Cliente nuevo = new Cliente(id, nombre, email);
            this.clientes.put(id, nuevo);
            return;
        }
        throw new Exception("Error: Ya existe un cliente con esa ID");
    }

    public void actualizarCliente(String id, String nombre, String email) throws Exception {
        Cliente cliente = obtenerCliente(id);
        cliente.setNombre(nombre);
        cliente.setEmail(email);
    }
    
    public void borrarCliente(String id) throws Exception {
        if (this.clientes.containsKey(id)) {
            Cliente clienteAEliminar = this.clientes.get(id);
            Map<Integer, OrdenCompra> ordenesDelCliente = clienteAEliminar.getOrdenes();
            for (Integer numeroOrden : ordenesDelCliente.keySet()) {
                this.ordenes.remove(numeroOrden);
            }
            this.clientes.remove(id);
            return;
        }
        
        throw new Exception("Error: No existe un cliente con esa ID");
    }
}