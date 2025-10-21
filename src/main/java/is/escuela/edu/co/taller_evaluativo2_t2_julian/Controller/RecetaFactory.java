package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

public class RecetaFactory {

    public static String crearReceta(String tipo, String receta) {
        RecetaValidationStrategy validador;

        if (tipo.equalsIgnoreCase("CHEF")) {
            validador = new ChefValidation();
        } else if (tipo.equalsIgnoreCase("PARTICIPANTE")) {
            validador = new ParticipanteValidation();
        } else {
            validador = new TelevidenteValidation();
        }

        return validador.validar(receta);
    }
}
