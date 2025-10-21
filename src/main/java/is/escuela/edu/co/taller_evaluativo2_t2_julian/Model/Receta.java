package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

public class Receta {
    private String nombre;
    private String descripcion;
    private TipoPersonaje tipoPersonaje;
    private int tiempoPreparacion;
    private boolean aprobada;

    public Receta(String nombre, String descripcion, TipoPersonaje tipoPersonaje, int tiempoPreparacion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoPersonaje = tipoPersonaje;
        this.tiempoPreparacion = tiempoPreparacion;
        this.aprobada = false;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoPersonaje getTipoPersonaje() {
        return tipoPersonaje;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void aprobar() {
        this.aprobada = true;
    }

    @Override
    public String toString() {
        return "Receta: " + nombre +
                " (" + tipoPersonaje + "), " +
                "Tiempo: " + tiempoPreparacion + " min, " +
                "Aprobada: " + aprobada;
    }
}
