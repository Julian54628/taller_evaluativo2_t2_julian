package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

public class ChefValidation implements RecetaValidationStrategy {

    @Override
    public String validar(String receta) {
        return "La receta del chef es validada: " + receta;
    }
}
