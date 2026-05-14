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
    
 // MÉTODOS DE PRODUCTOS

    public List<Producto> obtenerListadoProductos() {
        ArrayList<Producto> listaDeProductos = new ArrayList<>(this.productos.values());
        return listaDeProductos;
    }

    public void crearProducto(String nombre, float existencias, String unidad, double precio) {
        Producto nuevo = new Producto(nombre, existencias, unidad, precio);
        this.productos.put(nuevo.getCodigo(), nuevo);
    }

    public Producto obtenerProducto(int codigoProducto) throws Exception {
        if (this.productos.containsKey(codigoProducto)) {
            return this.productos.get(codigoProducto);
        }
        throw new Exception("Error: El producto con ese código no existe.");
    }

    public void actualizarProducto(int codigo, String nombre, float existencias, String unidad, double precio) throws Exception {
        Producto p = obtenerProducto(codigo);
        p.setNombre(nombre);
        p.setExistencias(existencias);
        p.setUnidad(unidad);
        p.setPrecio(precio);
    }

    public void borrarProducto(int codigo) throws Exception {
        if (this.productos.containsKey(codigo)) {
            this.productos.remove(codigo);
            return;
        }
        throw new Exception("Error: Producto no encontrado para borrar.");
    }

    // MÉTODOS DE ÓRDENES 

    public List<OrdenCompra> obtenerListadoOrdenes() {
        ArrayList<OrdenCompra> listaDeOrdenes = new ArrayList<>(this.ordenes.values());
        return listaDeOrdenes;
    }

    public OrdenCompra obtenerOrden(int numero) throws Exception {
        if (this.ordenes.containsKey(numero)) {
            return this.ordenes.get(numero);
        }
        throw new Exception("Error: Esa orden no existe en el sistema.");
    }

    public void crearOrdenVacia(String idCliente) throws Exception {
        Cliente cliente = obtenerCliente(idCliente);
        OrdenCompra nueva = new OrdenCompra(cliente);
        
        cliente.agregarOrden(nueva);
        this.ordenes.put(nueva.getNumero(), nueva);
    }

    public void borrarOrdenCompra(int numero) throws Exception {
        OrdenCompra orden = obtenerOrden(numero);
        orden.getCliente().borrarOrden(numero);
        this.ordenes.remove(numero);
    }

    public void establecerOrdenPendiente(int numero) throws Exception {
        obtenerOrden(numero).pasarAOrdenPendiente();
    }

    public void establecerOrdenTerminada(int numero) throws Exception {
        obtenerOrden(numero).terminarOrden();
    }

    // MÉTODOS DE LÍNEAS

    public void agregarLinea(int numeroOrden, int codigoProducto, double cantidad) throws Exception {
        OrdenCompra orden = obtenerOrden(numeroOrden);
        Producto producto = obtenerProducto(codigoProducto);
        orden.agregarLinea(producto, cantidad);
    }

    public void actualizarLinea(int numeroOrden, int numeroLinea, int codigoProducto, double cantidad) throws Exception {
        OrdenCompra orden = obtenerOrden(numeroOrden);
        Producto producto = obtenerProducto(codigoProducto);
        
        if (numeroLinea >= 0 && numeroLinea < orden.getLineas().size()) {
            orden.getLineas().remove(numeroLinea);
            orden.getLineas().add(numeroLinea, new Linea(producto, cantidad));
            return;
        }
        throw new Exception("Error: La línea es inválida");
    }

    public void borrarLinea(int numeroOrden, int numeroLinea) throws Exception {
        obtenerOrden(numeroOrden).borrarLinea(numeroLinea);
    }

    public List<Linea> obtenerlineasOrden(int numero) throws Exception {
        return obtenerOrden(numero).getLineas();
    }

    // OTROS

    public double obtenerMontoTotalPendiente() {
        double total = 0;
        for (OrdenCompra o : this.ordenes.values()) {
            if (o.getEstado().equalsIgnoreCase("Pendiente")) {
                total += o.calcularMontoTotal();
            }
        }
        return total;
    }

    public List<OrdenCompra> obtenerListadoOrdenesCliente(String id) throws Exception {
        return new ArrayList<>(obtenerCliente(id).getOrdenes().values());
    }

    public List<OrdenCompra> obtenerListadoOrdenesIniciadasCliente(String id) throws Exception {
        List<OrdenCompra> listaFiltrada = new ArrayList<>();
        for (OrdenCompra o : obtenerListadoOrdenesCliente(id)) {
            if (o.getEstado().equalsIgnoreCase("Iniciada")) listaFiltrada.add(o);
        }
        return listaFiltrada;
    }

    public List<OrdenCompra> obtenerListadoOrdenesPendientesCliente(String id) throws Exception {
        List<OrdenCompra> listaFiltrada = new ArrayList<>();
        for (OrdenCompra o : obtenerListadoOrdenesCliente(id)) {
            if (o.getEstado().equalsIgnoreCase("Pendiente")) listaFiltrada.add(o);
        }
        return listaFiltrada;
    }

    public List<OrdenCompra> obtenerListadoOrdenesTerminadasCliente(String id) throws Exception {
        List<OrdenCompra> listaFiltrada = new ArrayList<>();
        for (OrdenCompra o : obtenerListadoOrdenesCliente(id)) {
            if (o.getEstado().equalsIgnoreCase("Terminada")) listaFiltrada.add(o);
        }
        return listaFiltrada;
    }
 }