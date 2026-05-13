package logica;

public class Producto {
    private static int consecutivo = 1;
    private int codigoProducto; 
    private String nombre;
    private float existencias;
    private String unidad;
    private double precio;

    public Producto(String nombre, float existencias, String unidad, double precio) {
        codigoProducto = consecutivo++;
        this.nombre = nombre;
        this.existencias = existencias;
        this.unidad = unidad;
        this.precio = precio;
    }
}