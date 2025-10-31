package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import java.util.List;

@Document(collection = "recetas")
public class Receta {
    @Id
    private String id;
    private int consecutivo;
    private String titulo;
    private List<Ingrediente> ingredientes;
    private List<String> pasosPreparacion;

    @DBRef
    private Usuario cocinero;

    private String temporada;
    private boolean aprobada;

    public Receta() {
        this.aprobada = false;
    }

    public Receta(int consecutivo, String titulo, List<Ingrediente> ingredientes,
                  List<String> pasosPreparacion, Usuario cocinero, String temporada) {
        this.consecutivo = consecutivo;
        this.titulo = titulo;
        this.ingredientes = ingredientes;
        this.pasosPreparacion = pasosPreparacion;
        this.cocinero = cocinero;
        this.temporada = temporada;
        this.aprobada = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(int consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPasosPreparacion() {
        return pasosPreparacion;
    }

    public void setPasosPreparacion(List<String> pasosPreparacion) {
        this.pasosPreparacion = pasosPreparacion;
    }

    public Usuario getCocinero() {
        return cocinero;
    }

    public void setCocinero(Usuario cocinero) {
        this.cocinero = cocinero;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }
}