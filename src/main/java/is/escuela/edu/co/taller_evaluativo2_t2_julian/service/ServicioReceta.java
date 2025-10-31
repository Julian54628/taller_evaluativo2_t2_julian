package is.escuela.edu.co.taller_evaluativo2_t2_julian.Servicio;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.Receta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.FabricaReceta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.Usuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.TipoCocinero;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.Ingrediente;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Repositorio.RepositorioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioReceta {

    @Autowired
    private RepositorioReceta repositorioReceta;

    @Autowired
    private FabricaReceta fabricaReceta;

    @Autowired
    private ServicioUsuario servicioUsuario;

    private int consecutivo = 1;

    public Receta registrarRecetaTelevidente(String titulo, List<Ingrediente> ingredientes,
                                             List<String> pasosPreparacion, String usuarioId) {
        Usuario televidente = servicioUsuario.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Receta receta = fabricaReceta.crearRecetaTelevidente(titulo, ingredientes, pasosPreparacion, televidente);
        receta.setConsecutivo(consecutivo++);
        return repositorioReceta.save(receta);
    }

    public Receta registrarRecetaParticipante(String titulo, List<Ingrediente> ingredientes,
                                              List<String> pasosPreparacion, String usuarioId, String temporada) {
        Usuario participante = servicioUsuario.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Receta receta = fabricaReceta.crearRecetaParticipante(titulo, ingredientes, pasosPreparacion, participante, temporada);
        receta.setConsecutivo(consecutivo++);
        return repositorioReceta.save(receta);
    }

    public Receta registrarRecetaCocinero(String titulo, List<Ingrediente> ingredientes,
                                          List<String> pasosPreparacion, String usuarioId) {
        Usuario cocinero = servicioUsuario.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Receta receta = fabricaReceta.crearRecetaCocinero(titulo, ingredientes, pasosPreparacion, cocinero);
        receta.setConsecutivo(consecutivo++);
        return repositorioReceta.save(receta);
    }

    public List<Receta> obtenerTodasLasRecetas() {
        return repositorioReceta.findAll();
    }

    public Optional<Receta> obtenerRecetaPorConsecutivo(int consecutivo) {
        return repositorioReceta.findByConsecutivo(consecutivo);
    }

    public List<Receta> obtenerRecetasDeParticipantes() {
        return repositorioReceta.findByTipoCocinero(TipoCocinero.PARTICIPANTE);
    }

    public List<Receta> obtenerRecetasDeTelevidentes() {
        return repositorioReceta.findByTipoCocinero(TipoCocinero.TELEVIDENTE);
    }

    public List<Receta> obtenerRecetasDeCocineros() {
        return repositorioReceta.findByTipoCocinero(TipoCocinero.COMPETIDOR);
    }

    public List<Receta> obtenerRecetasPorTemporada(String temporada) {
        return repositorioReceta.findByTemporada(temporada);
    }

    public List<Receta> buscarRecetasPorIngrediente(String ingrediente) {
        return repositorioReceta.findByIngredienteContaining(ingrediente);
    }

    public boolean eliminarReceta(String id) {
        if (repositorioReceta.existsById(id)) {
            repositorioReceta.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Receta> actualizarReceta(String id, Receta recetaActualizada) {
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
        return repositorioReceta.findById(id)
                .map(receta -> {
                    receta.setAprobada(true);
                    return repositorioReceta.save(receta);
                });
    }

    public List<Receta> obtenerRecetasAprobadas() {
        return repositorioReceta.findByAprobada(true);
    }

    public List<Receta> obtenerRecetasPorCocinero(String cocineroId) {
        return repositorioReceta.findByCocineroId(cocineroId);
    }
}