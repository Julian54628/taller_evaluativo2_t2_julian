package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String documento;
    private String celular;
    private String correo;
    private TipoCocinero tipoCocinero;
    private String temporada;

    public Usuario() {}

    public Usuario(String nombre, String documento, String celular, String correo, TipoCocinero tipoCocinero, String temporada) {
        this.nombre = nombre;
        this.documento = documento;
        this.celular = celular;
        this.correo = correo;
        this.tipoCocinero = tipoCocinero;
        this.temporada = temporada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public TipoCocinero getTipoCocinero() {
        return tipoCocinero;
    }

    public void setTipoCocinero(TipoCocinero tipoCocinero) {
        this.tipoCocinero = tipoCocinero;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }
}