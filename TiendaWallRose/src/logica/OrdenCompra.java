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
        this.numero = consecutivo++; 
        fecha = LocalDateTime.now();
        estado = "Iniciada";
        impuesto = 0.13;
        this.cliente = cliente;
        lineas = new ArrayList<Linea>();
    }
}