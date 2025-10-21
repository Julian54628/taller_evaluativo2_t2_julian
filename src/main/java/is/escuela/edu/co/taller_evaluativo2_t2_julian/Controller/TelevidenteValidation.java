package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

public class TelevidenteValidation implements RecetaValidationStrategy {

    @Override
    public String validar(String receta) {
        return "La receta del televidente es validada: " + receta;
    }
}
