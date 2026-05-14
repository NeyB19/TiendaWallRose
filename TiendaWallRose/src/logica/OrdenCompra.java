package logica;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdenCompra {
    private static int consecutivo = 1;
    private int numero;
    private LocalDateTime fecha;
    private String estado;
    private double impuesto;
    private ArrayList<Linea> lineas;
    private Cliente cliente;

    public OrdenCompra(Cliente cliente) {
        numero = consecutivo++;
        this.fecha = LocalDateTime.now();
        this.estado = "Iniciada";
        this.impuesto = 0.13;
        this.cliente = cliente;
        lineas = new ArrayList<Linea>();
    }


    public void agregarLinea(Producto producto, double cantidad) {
        Linea nuevaLinea = new Linea(producto, cantidad);
        this.lineas.add(nuevaLinea);
    }

    public void borrarLinea(int numeroDeLinea) throws Exception {
        if (numeroDeLinea >= 0 && numeroDeLinea < this.lineas.size()) {
            this.lineas.remove(numeroDeLinea);
            return;
        }
        throw new Exception("Error: Esa línea no es válida");
    }

    public void terminarOrden() {
        this.estado = "Terminada";
    }

    public double calcularMonto() {
        double subtotal = 0;
        for (Linea l : lineas) {
            subtotal = subtotal + l.calcularCosto();
        }
        return subtotal;
    }

    public double calcularMontoImpuesto() {
        return calcularMonto() * this.impuesto;
    }

    public double calcularMontoTotal() {
        return calcularMonto() + calcularMontoImpuesto();
    }

    public void pasarAOrdenPendiente() {
        this.estado = "Pendiente";
    }

    // GETTERS Y SETTERS 

    public int getNumero() {
        return this.numero;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Linea> getLineas() {
        return this.lineas;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    @Override
    public String toString() {
        return "\n  ORDEN DE COMPRA #" + numero +
               "\nFecha: " + fecha +
               "\nEstado: " + estado +
               "\nCliente: " + cliente.getNombre() +
               "\nTotal de productos: " + lineas.size() +
               "\nSubtotal: " + calcularMonto() +
               "\nImpuestos (13%): " + calcularMontoImpuesto() +
               "\nTOTAL FINAL: " + calcularMontoTotal() +
               "\n----------------------------";
    }
}