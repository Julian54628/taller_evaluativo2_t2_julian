package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Receta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Ingrediente;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.service.ServicioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
@CrossOrigin(origins = "*")
public class ControladorReceta {

    @Autowired
    private ServicioReceta servicioReceta;

    @PostMapping("/televidente/{usuarioId}")
    public ResponseEntity<Receta> registrarRecetaTelevidente(
            @RequestBody Map<String, Object> solicitud,
            @PathVariable String usuarioId) {
        try {
            Receta receta = servicioReceta.registrarRecetaTelevidente(
                    (String) solicitud.get("titulo"),
                    (List<Ingrediente>) solicitud.get("ingredientes"),
                    (List<String>) solicitud.get("pasosPreparacion"),
                    usuarioId
            );
            return ResponseEntity.ok(receta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/participante/{usuarioId}")
    public ResponseEntity<Receta> registrarRecetaParticipante(
            @RequestBody Map<String, Object> solicitud,
            @PathVariable String usuarioId) {
        try {
            Receta receta = servicioReceta.registrarRecetaParticipante(
                    (String) solicitud.get("titulo"),
                    (List<Ingrediente>) solicitud.get("ingredientes"),
                    (List<String>) solicitud.get("pasosPreparacion"),
                    usuarioId,
                    (String) solicitud.get("temporada")
            );
            return ResponseEntity.ok(receta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cocinero/{usuarioId}")
    public ResponseEntity<Receta> registrarRecetaCocinero(
            @RequestBody Map<String, Object> solicitud,
            @PathVariable String usuarioId) {
        try {
            Receta receta = servicioReceta.registrarRecetaCocinero(
                    (String) solicitud.get("titulo"),
                    (List<Ingrediente>) solicitud.get("ingredientes"),
                    (List<String>) solicitud.get("pasosPreparacion"),
                    usuarioId
            );
            return ResponseEntity.ok(receta);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Receta>> obtenerTodasLasRecetas() {
        List<Receta> recetas = servicioReceta.obtenerTodasLasRecetas();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/consecutivo/{consecutivo}")
    public ResponseEntity<Receta> obtenerRecetaPorConsecutivo(@PathVariable int consecutivo) {
        Optional<Receta> receta = servicioReceta.obtenerRecetaPorConsecutivo(consecutivo);
        return receta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/participantes")
    public ResponseEntity<List<Receta>> obtenerRecetasDeParticipantes() {
        List<Receta> recetas = servicioReceta.obtenerRecetasDeParticipantes();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/televidentes")
    public ResponseEntity<List<Receta>> obtenerRecetasDeTelevidentes() {
        List<Receta> recetas = servicioReceta.obtenerRecetasDeTelevidentes();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/cocineros")
    public ResponseEntity<List<Receta>> obtenerRecetasDeCocineros() {
        List<Receta> recetas = servicioReceta.obtenerRecetasDeCocineros();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/temporada/{temporada}")
    public ResponseEntity<List<Receta>> obtenerRecetasPorTemporada(@PathVariable String temporada) {
        List<Receta> recetas = servicioReceta.obtenerRecetasPorTemporada(temporada);
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Receta>> buscarRecetasPorIngrediente(@RequestParam String ingrediente) {
        List<Receta> recetas = servicioReceta.buscarRecetasPorIngrediente(ingrediente);
        return ResponseEntity.ok(recetas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable String id) {
        boolean eliminada = servicioReceta.eliminarReceta(id);
        return eliminada ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(@PathVariable String id, @RequestBody Receta receta) {
        Optional<Receta> recetaActualizada = servicioReceta.actualizarReceta(id, receta);
        return recetaActualizada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Receta> aprobarReceta(@PathVariable String id) {
        Optional<Receta> recetaAprobada = servicioReceta.aprobarReceta(id);
        return recetaAprobada.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/aprobadas")
    public ResponseEntity<List<Receta>> obtenerRecetasAprobadas() {
        List<Receta> recetas = servicioReceta.obtenerRecetasAprobadas();
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/cocinero/{cocineroId}")
    public ResponseEntity<List<Receta>> obtenerRecetasPorCocinero(@PathVariable String cocineroId) {
        List<Receta> recetas = servicioReceta.obtenerRecetasPorCocinero(cocineroId);
        return ResponseEntity.ok(recetas);
    }
}