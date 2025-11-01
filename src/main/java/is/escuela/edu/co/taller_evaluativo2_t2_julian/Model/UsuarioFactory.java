package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

import org.springframework.stereotype.Component;

@Component
public class UsuarioFactory {

    public Usuario crearTelevidente(String nombre, String documento, String celular, String correo) {
        return new Usuario(nombre, documento, celular, correo, TipoCocinero.TELEVIDENTE, null);
    }

    public Usuario crearParticipante(String nombre, String documento, String celular, String correo, String temporada) {
        return new Usuario(nombre, documento, celular, correo, TipoCocinero.PARTICIPANTE, temporada);
    }

    public Usuario crearCocineroJurado(String nombre, String documento, String celular, String correo) {
        return new Usuario(nombre, documento, celular, correo, TipoCocinero.COMPETIDOR, null);
    }
}