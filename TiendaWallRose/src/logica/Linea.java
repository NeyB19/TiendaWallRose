package logica;

public class Linea {
    private double cantidad;
    private Producto producto;

    public Linea(Producto producto, double cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
}