package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

public class ParticipanteValidation implements RecetaValidationStrategy {

    @Override
    public String validar(String receta) {
        return "La receta del participante es validada: " + receta;
    }
}
