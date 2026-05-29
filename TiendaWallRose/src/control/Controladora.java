package control;

import logica.Cliente;
import logica.Linea;
import logica.Producto;
import logica.OrdenCompra;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Controladora implements Serializable {
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
        Cliente auxiliar = new Cliente(id, nombre, email);  
        // validar email
        if (!auxiliar.validarEmail(email)) {
            throw new Exception("Error: El formato del correo electrónico no es válido");
        }
        if (!this.clientes.containsKey(id)) {
            this.clientes.put(id, auxiliar);
            return;
        }
        throw new Exception("Error: Ya existe un cliente con esa ID");
    }

    public void actualizarCliente(String id, String nombre, String email) throws Exception {
        Cliente cliente = obtenerCliente(id);       
        if (!cliente.validarEmail(email)) {
            throw new Exception("Error: El formato del correo electrónico no es válido.");
        }
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
    	// restaurar inventario
        for (Linea linea : orden.getLineas()) {
            Producto p = linea.getProducto();
            p.setExistencias((float) (p.getExistencias() + linea.getCantidad()));
        }
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

        // Descontar del inventario
        float nuevasExistencias = (float) (producto.getExistencias() - cantidad);
        producto.setExistencias(nuevasExistencias);
    }

    public void actualizarLinea(int numeroOrden, int numeroLinea, int codigoProducto, double cantidad) throws Exception {
        OrdenCompra orden = obtenerOrden(numeroOrden);
        Producto producto = obtenerProducto(codigoProducto);
        if (numeroLinea >= 0 && numeroLinea < orden.getLineas().size()) {
            Linea lineaVieja = orden.getLineas().get(numeroLinea);
            double cantidadAnterior = lineaVieja.getCantidad();
            
            producto.setExistencias((float) (producto.getExistencias() + cantidadAnterior));
            orden.getLineas().remove(numeroLinea);
            orden.getLineas().add(numeroLinea, new Linea(producto, cantidad));

            producto.setExistencias((float) (producto.getExistencias() - cantidad));
            return;
        }
        throw new Exception("Error: La línea es inválida");
    }
    public void borrarLinea(int numeroOrden, int numeroLinea) throws Exception {
    	OrdenCompra orden = obtenerOrden(numeroOrden);
        
        if (numeroLinea >= 0 && numeroLinea < orden.getLineas().size()) {
            Linea lineaAEliminar = orden.getLineas().get(numeroLinea);
            Producto producto = lineaAEliminar.getProducto();
            
            // restaurar el inventario
            producto.setExistencias((float) (producto.getExistencias() + lineaAEliminar.getCantidad()));
            orden.borrarLinea(numeroLinea);
            return;
        }
        throw new Exception("Error: Línea inválida");
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
    
    public static void guardarDatos() throws java.io.IOException {
		java.io.FileOutputStream file = new java.io.FileOutputStream("DatosTiendaWallRose.dat");
		java.io.ObjectOutputStream stream = new java.io.ObjectOutputStream(file);		
		stream.writeObject(instance); 		
		stream.writeInt(logica.OrdenCompra.getConsecutivo());
		stream.writeInt(logica.Producto.getConsecutivo());
		stream.close();
		file.close();
	}

	public static void cargarDatos() throws java.io.IOException, ClassNotFoundException {
		try {
			java.io.FileInputStream file = new java.io.FileInputStream("DatosTiendaWallRose.dat");
			java.io.ObjectInputStream stream = new java.io.ObjectInputStream(file);
			instance = (Controladora) stream.readObject(); 
			int proximoConsecutivo = stream.readInt();
			logica.OrdenCompra.setConsecutivo(proximoConsecutivo);
			int proximoConsecutivoProd = stream.readInt();
			logica.Producto.setConsecutivo(proximoConsecutivoProd);
			stream.close();
			file.close();
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Archivo de datos no encontrado");
		}
	}
}