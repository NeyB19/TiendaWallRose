package logica;

public class Linea {
    private double cantidad;
    private Producto producto;

    public Linea(Producto producto, double cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }


    public double calcularCosto() {
        double costoTotal = 0;
        if (this.producto != null) {
            costoTotal = this.producto.getPrecio() * this.cantidad;
        }
        return costoTotal;
    }

    public double getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(double cantidad) throws Exception {
        if (cantidad > 0) {
            this.cantidad = cantidad;
            return;
        }
        throw new Exception("Error: La cantidad debe ser mayor a cero.");
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) throws Exception {
        if (producto != null) {
            this.producto = producto;
            return;
        }
        throw new Exception("Error: Se debe asignar un producto.");
    }

 
    public String toString() {
        return "\n  DETALLE DE LA LÍNEA: " +
               "\nProducto: " + this.producto.getNombre() +
               "\nCantidad: " + cantidad +
               "\nPrecio unitario: " + this.producto.getPrecio() +
               "\nCosto total: " + calcularCosto() +
               "\n----------------------------";
    }
}