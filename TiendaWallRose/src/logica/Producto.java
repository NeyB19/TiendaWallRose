package logica;

public class Producto {
    private static int consecutivo = 1;
    private int codigoProducto;
    private String nombre;
    private float existencias;
    private String unidad;
    private double precio;

    public Producto(int codigoProducto, String nombre, float existencias, String unidad, double precio) {
        codigoProducto = consecutivo++;
        this.nombre = nombre;
        this.existencias = existencias;
        this.unidad = unidad;
        this.precio = precio;
    }

    // GETTERS Y SETTERS

    public int getCodigo() {
        return codigoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public float getExistencias() {
        return existencias;
    }

    public String getUnidad() {
        return unidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setExistencias(float existencias) throws Exception {
        if (existencias >= 0) {
            this.existencias = existencias;
            return;
        }
        throw new Exception("Error: Las existencias no pueden ser valores negativos.");
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public void setPrecio(double precio) throws Exception {
        if (precio > 0) {
            this.precio = precio;
            return;
        }
        throw new Exception("Error: El precio debe ser un valor mayor a cero.");
    }

    public String toString() {
        return "\n  DATOS DEL PRODUCTO: " + 
               "\nCódigo: " + codigoProducto +
               "\nNombre: " + nombre +
               "\nExistencias: " + existencias +
               "\nUnidad: " + unidad +
               "\nPrecio: " + precio +
               "\n----------------------------";
    }
}