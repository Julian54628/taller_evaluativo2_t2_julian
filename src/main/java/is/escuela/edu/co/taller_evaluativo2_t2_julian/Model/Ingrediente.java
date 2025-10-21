package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

public class Ingrediente {
    private String nombre;
    private double cantidad;
    private String unidad;

    public Ingrediente(String nombre, double cantidad, String unidad) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.unidad = unidad;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    @Override
    public String toString() {
        return cantidad + " " + unidad + " de " + nombre;
    }
}
