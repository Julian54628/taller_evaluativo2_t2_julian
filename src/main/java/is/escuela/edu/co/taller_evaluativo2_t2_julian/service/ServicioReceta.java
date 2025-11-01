package is.escuela.edu.co.taller_evaluativo2_t2_julian.service;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Receta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.RecetaFactory;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Usuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.TipoCocinero;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Ingrediente;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.repository.RepositorioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    @Autowired
    private RecetaFactory RecetaFactory;

    @Autowired
    private ServicioUsuario servicioUsuario;

    private int consecutivo = 1;

    public Receta registrarRecetaTelevidente(String titulo, List<Ingrediente> ingredientes,
                                             List<String> pasosPreparacion, String usuarioId) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException("El título no puede estar vacío");
        }
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new RuntimeException("Los ingredientes no pueden estar vacíos");
        }
        Usuario televidente = servicioUsuario.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Receta receta = RecetaFactory.crearRecetaTelevidente(titulo, ingredientes, pasosPreparacion, televidente);
        receta.setConsecutivo(consecutivo++);
        return repositorioReceta.save(receta);
    }

    public Receta registrarRecetaParticipante(String titulo, List<Ingrediente> ingredientes,
                                              List<String> pasosPreparacion, String usuarioId, String temporada) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException("El título no puede estar vacío");
        }
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new RuntimeException("Los ingredientes no pueden estar vacíos");
        }
        if (temporada == null || temporada.trim().isEmpty()) {
            throw new RuntimeException("La temporada no puede estar vacía");
        }
        Usuario participante = servicioUsuario.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Receta receta = RecetaFactory.crearRecetaParticipante(titulo, ingredientes, pasosPreparacion, participante, temporada);
        receta.setConsecutivo(consecutivo++);
        return repositorioReceta.save(receta);
    }

    public Receta registrarRecetaCocinero(String titulo, List<Ingrediente> ingredientes,
                                          List<String> pasosPreparacion, String usuarioId) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new RuntimeException("El título no puede estar vacío");
        }
        if (ingredientes == null || ingredientes.isEmpty()) {
            throw new RuntimeException("Los ingredientes no pueden estar vacíos");
        }
        Usuario cocinero = servicioUsuario.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Receta receta = RecetaFactory.crearRecetaCocinero(titulo, ingredientes, pasosPreparacion, cocinero);
        receta.setConsecutivo(consecutivo++);
        return repositorioReceta.save(receta);
    }

    public List<Receta> obtenerTodasLasRecetas() {
        List<Receta> recetas = repositorioReceta.findAll();
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas");
        }
        return recetas;
    }

    public Optional<Receta> obtenerRecetaPorConsecutivo(int consecutivo) {
        Optional<Receta> receta = repositorioReceta.findByConsecutivo(consecutivo);
        if (receta.isEmpty()) {
            throw new RuntimeException("Receta con consecutivo " + consecutivo + " no encontrada");
        }
        return receta;
    }

    public List<Receta> obtenerRecetasDeParticipantes() {
        List<Receta> recetas = repositorioReceta.findByTipoCocinero(TipoCocinero.PARTICIPANTE);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas de participantes");
        }
        return recetas;
    }

    public List<Receta> obtenerRecetasDeTelevidentes() {
        List<Receta> recetas = repositorioReceta.findByTipoCocinero(TipoCocinero.TELEVIDENTE);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas de televidentes");
        }
        return recetas;
    }

    public List<Receta> obtenerRecetasDeCocineros() {
        List<Receta> recetas = repositorioReceta.findByTipoCocinero(TipoCocinero.COMPETIDOR);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas de cocineros");
        }
        return recetas;
    }

    public List<Receta> obtenerRecetasPorTemporada(String temporada) {
        if (temporada == null || temporada.trim().isEmpty()) {
            throw new RuntimeException("La temporada no puede estar vacía");
        }
        List<Receta> recetas = repositorioReceta.findByTemporada(temporada);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas para la temporada: " + temporada);
        }
        return recetas;
    }

    public List<Receta> buscarRecetasPorIngrediente(String ingrediente) {
        if (ingrediente == null || ingrediente.trim().isEmpty()) {
            throw new RuntimeException("El ingrediente no puede estar vacío");
        }
        List<Receta> recetas = repositorioReceta.findByIngredienteContaining(ingrediente);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas con el ingrediente: " + ingrediente);
        }
        return recetas;
    }

    public boolean eliminarReceta(String id) {
        if (!repositorioReceta.existsById(id)) {
            throw new RuntimeException("Receta con ID " + id + " no encontrada");
        }
        repositorioReceta.deleteById(id);
        return true;
    }

    public Optional<Receta> actualizarReceta(String id, Receta recetaActualizada) {
        if (!repositorioReceta.existsById(id)) {
            throw new RuntimeException("Receta con ID " + id + " no encontrada");
        }
        return repositorioReceta.findById(id)
                .map(recetaExistente -> {
                    actualizarCamposReceta(recetaExistente, recetaActualizada);
                    return repositorioReceta.save(recetaExistente);
                });
    }

    private void actualizarCamposReceta(Receta existente, Receta actualizada) {
        if (actualizada.getTitulo() != null) existente.setTitulo(actualizada.getTitulo());
        if (actualizada.getIngredientes() != null) existente.setIngredientes(actualizada.getIngredientes());
        if (actualizada.getPasosPreparacion() != null) existente.setPasosPreparacion(actualizada.getPasosPreparacion());
        if (actualizada.getTemporada() != null) existente.setTemporada(actualizada.getTemporada());
        existente.setAprobada(actualizada.isAprobada());
    }

    public Optional<Receta> aprobarReceta(String id) {
        if (!repositorioReceta.existsById(id)) {
            throw new RuntimeException("Receta con ID " + id + " no encontrada");
        }
        return repositorioReceta.findById(id)
                .map(receta -> {
                    receta.setAprobada(true);
                    return repositorioReceta.save(receta);
                });
    }

    public List<Receta> obtenerRecetasAprobadas() {
        List<Receta> recetas = repositorioReceta.findByAprobada(true);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas aprobadas");
        }
        return recetas;
    }

    public List<Receta> obtenerRecetasPorCocinero(String cocineroId) {
        if (cocineroId == null || cocineroId.trim().isEmpty()) {
            throw new RuntimeException("El ID del cocinero no puede estar vacío");
        }
        List<Receta> recetas = repositorioReceta.findByCocineroId(cocineroId);
        if (recetas.isEmpty()) {
            throw new RuntimeException("No se encontraron recetas para el cocinero con ID: " + cocineroId);
        }
        return recetas;
    }
}