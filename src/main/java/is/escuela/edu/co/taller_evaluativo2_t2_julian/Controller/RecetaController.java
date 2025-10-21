package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

import java.util.ArrayList;

public class RecetaController {

    private ArrayList<String> recetas = new ArrayList<>();

    public String registrarRecetaTelevidente(String receta) {
        String nuevaReceta = RecetaFactory.crearReceta("TELEVIDENTE", receta);
        recetas.add(nuevaReceta);
        return "Se guardo al receta del televidente";
    }

    public String registrarRecetaParticipante(String receta) {
        String nuevaReceta = RecetaFactory.crearReceta("PARTICIPANTE", receta);
        recetas.add(nuevaReceta);
        return "Se guardo al receta del participante";
    }

    public String registrarRecetaChef(String receta) {
        String nuevaReceta = RecetaFactory.crearReceta("CHEF", receta);
        recetas.add(nuevaReceta);
        return "Se guardo al receta del chef";
    }

    public ArrayList<String> mostrarRecetas() {
        return recetas;
    }

    public String eliminarReceta(int posicion) {
        if (posicion < 0 || posicion >= recetas.size()) {
            return "En esta posicion no se encuentra ninguna receta";
        }
        recetas.remove(posicion);
        return "Se elimino la receta";
    }

    public String actualizarReceta(int posicion, String nuevaReceta) {
        if (posicion < 0 || posicion >= recetas.size()) {
            return "En esta posicion no se encuentra ninguna receta";
        }
        recetas.set(posicion, nuevaReceta);
        return "Se actualizo la receta";
    }
}
