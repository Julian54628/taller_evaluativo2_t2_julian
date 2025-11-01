package is.escuela.edu.co.taller_evaluativo2_t2_julian.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecetaFactory {

    @Autowired
    private ValidadorReceta validadorReceta;

    public Receta crearReceta(String titulo, List<Ingrediente> ingredientes,
                              List<String> pasosPreparacion, Usuario cocinero, String temporada) {

        Receta receta = new Receta(0, titulo, ingredientes, pasosPreparacion, cocinero, temporada);

        String resultadoValidacion = validadorReceta.validar(receta);
        if (!resultadoValidacion.equals("Receta válida")) {
            throw new RuntimeException(resultadoValidacion);
        }

        return receta;
    }

    public Receta crearRecetaTelevidente(String titulo, List<Ingrediente> ingredientes,
                                         List<String> pasosPreparacion, Usuario televidente) {
        return crearReceta(titulo, ingredientes, pasosPreparacion, televidente, null);
    }

    public Receta crearRecetaParticipante(String titulo, List<Ingrediente> ingredientes,
                                          List<String> pasosPreparacion, Usuario participante, String temporada) {
        return crearReceta(titulo, ingredientes, pasosPreparacion, participante, temporada);
    }

    public Receta crearRecetaCocinero(String titulo, List<Ingrediente> ingredientes,
                                      List<String> pasosPreparacion, Usuario cocinero) {
        return crearReceta(titulo, ingredientes, pasosPreparacion, cocinero, null);
    }
}