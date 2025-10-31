package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuario {

    public String validar(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return "Error: El nombre es obligatorio";
        }
        if (usuario.getDocumento() == null || usuario.getDocumento().trim().isEmpty()) {
            return "Error: El documento es obligatorio";
        }
        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            return "Error: El correo es obligatorio";
        }
        if (usuario.getTipoCocinero() == null) {
            return "Error: El tipo de cocinero es obligatorio";
        }
        if (usuario.getTipoCocinero() == TipoCocinero.PARTICIPANTE &&
                (usuario.getTemporada() == null || usuario.getTemporada().trim().isEmpty())) {
            return "Error: Los participantes deben tener una temporada asignada";
        }

        return "Usuario válido";
    }
}