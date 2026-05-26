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
        try {
            // --- 1. Datos Semilla: Clientes ---
            this.crearCliente("1-1937-1210", "Andrea Ling", "andy3103@gmail.com");
            this.crearCliente("7-2522-8816", "Javier Acosta", "javiacosta@hotmail.com");
            this.crearCliente("5-3193-9237", "Lucía Berlanga", "bluci@gmail.com");
            
            // --- 2. Datos Semilla: Productos ---
            this.crearProducto("Papas Rojas", 150.5f, "kg", 850.00);
            this.crearProducto("Cable Eléctrico", 45.0f, "m", 1200.00);
            this.crearProducto("Tornillos 2 pulg", 500.0f, "unidades", 25.00);
            this.crearProducto("Cinta Aislante", 20.0f, "unidades", 950.00);
            
            // --- 3. Datos Semilla: Instancias para armar las líneas ---
            Cliente andrea = this.obtenerCliente("1-1937-1210");
            Cliente javier = this.obtenerCliente("7-2522-8816");
            
            Producto papas = this.productos.get(1);
            Producto cable = this.productos.get(2);
            Producto tornillos = this.productos.get(3);
            Producto cinta = this.productos.get(4);
            
            // ==========================================
            // ÓRDENES DE ANDREA LING (Productora de 3 estados)
            // ==========================================
            
            // Orden 1: Estado "Iniciada"
            logica.OrdenCompra orden1 = new logica.OrdenCompra(andrea);
            orden1.agregarLinea(papas, 5.0); 
            orden1.agregarLinea(tornillos, 50.0); 
            this.ordenes.put(orden1.getNumero(), orden1);
            andrea.agregarOrden(orden1);
            
            // Orden 2: Estado "Terminada"
            logica.OrdenCompra orden2 = new logica.OrdenCompra(andrea);
            orden2.agregarLinea(cinta, 2.0); // 2 cintas aislantes
            orden2.setEstado("Terminada");   // Forzamos el estado a Terminada
            this.ordenes.put(orden2.getNumero(), orden2);
            andrea.agregarOrden(orden2);
            
            // Orden 3: Estado "Pendiente"
            logica.OrdenCompra orden3 = new logica.OrdenCompra(andrea);
            orden3.agregarLinea(papas, 10.0); // 10 kg de papas adicionales
            orden3.setEstado("Pendiente");   // Forzamos el estado a Pendiente
            this.ordenes.put(orden3.getNumero(), orden3);
            andrea.agregarOrden(orden3);
            
            // ==========================================
            // ÓRDENES DE JAVIER ACOSTA
            // ==========================================
            
            // Orden 4: Estado "Pendiente"
            logica.OrdenCompra orden4 = new logica.OrdenCompra(javier);
            orden4.agregarLinea(cable, 12.5); 
            orden4.setEstado("Pendiente"); 
            this.ordenes.put(orden4.getNumero(), orden4);
            javier.agregarOrden(orden4);
            
            System.out.println("Controladora: ¡Estructura completa de órdenes semilla cargada!");
        }
        catch (Exception e) {
            System.out.println("Error inicializando datos semilla: " + e.toString());
        }

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
        this.obtenerProducto(codigo); 
        boolean enUso = this.estaProductoEnUso(codigo);
        if (enUso == true) {
            throw new Exception("Error: El producto no se puede borrar porque está en uso en una orden.");
        }
        this.productos.remove(codigo);
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
    
    public double obtenerMontoTotalPendientesCliente(String idCliente) throws Exception {
        Cliente clienteInteres = this.obtenerCliente(idCliente);
        double totalPendientes = 0;
        Map<Integer, OrdenCompra> ordenesCliente = clienteInteres.getOrdenes();
        for (OrdenCompra orden : ordenesCliente.values()) {
            String estadoOrden = orden.getEstado();
            if (estadoOrden.equalsIgnoreCase("Pendiente")) {
                double totalOrden = orden.calcularMontoTotal();               
                totalPendientes = totalPendientes + totalOrden;
            }
        }     
        return totalPendientes;
    }
    
    private boolean estaProductoEnUso(int codigo) {
        List<OrdenCompra> todasLasOrdenes = this.obtenerListadoOrdenes();
        for (OrdenCompra orden : todasLasOrdenes) {
            ArrayList<Linea> lineas = orden.getLineas();
            for (Linea linea : lineas) {
                Producto p = linea.getProducto();
                if (p.getCodigo() == codigo) {
                    return true;
                }
            }
        }
        
        return false;
    }
 }