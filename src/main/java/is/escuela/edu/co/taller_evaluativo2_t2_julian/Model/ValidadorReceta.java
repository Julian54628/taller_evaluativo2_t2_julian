package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

import org.springframework.stereotype.Component;

@Component
public class ValidadorReceta {

    public String validar(Receta receta) {
        if (receta.getTitulo() == null || receta.getTitulo().trim().isEmpty()) {
            return "Error: El título es obligatorio";
        }
        if (receta.getIngredientes() == null || receta.getIngredientes().isEmpty()) {
            return "Error: La receta debe tener al menos un ingrediente";
        }
        if (receta.getPasosPreparacion() == null || receta.getPasosPreparacion().isEmpty()) {
            return "Error: La receta debe tener al menos un paso de preparación";
        }
        if (receta.getCocinero() == null) {
            return "Error: La receta debe tener un cocinero asignado";
        }

        TipoCocinero tipoCocinero = receta.getCocinero().getTipoCocinero();

        if (tipoCocinero == TipoCocinero.PARTICIPANTE) {
            if (receta.getTemporada() == null || receta.getTemporada().trim().isEmpty()) {
                return "Error: Las recetas de participantes deben tener una temporada asignada";
            }
        }

        if (tipoCocinero == TipoCocinero.COMPETIDOR) {
            if (receta.getPasosPreparacion().size() < 3) {
                return "Error: Las recetas de cocineros deben tener al menos 3 pasos de preparación";
            }
        }

        return "Receta válida";
    }
}