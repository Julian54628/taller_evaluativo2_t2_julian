package is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Usuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.TipoCocinero;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.service.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class ControladorUsuario {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @PostMapping("/televidente")
    public ResponseEntity<Usuario> registrarTelevidente(@RequestBody Map<String, String> solicitud) {
        try {
            Usuario usuario = servicioUsuario.registrarTelevidente(
                    solicitud.get("nombre"),
                    solicitud.get("documento"),
                    solicitud.get("celular"),
                    solicitud.get("correo")
            );
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/participante")
    public ResponseEntity<Usuario> registrarParticipante(@RequestBody Map<String, String> solicitud) {
        try {
            Usuario usuario = servicioUsuario.registrarParticipante(
                    solicitud.get("nombre"),
                    solicitud.get("documento"),
                    solicitud.get("celular"),
                    solicitud.get("correo"),
                    solicitud.get("temporada")
            );
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cocinero")
    public ResponseEntity<Usuario> registrarCocineroJurado(@RequestBody Map<String, String> solicitud) {
        try {
            Usuario usuario = servicioUsuario.registrarCocineroJurado(
                    solicitud.get("nombre"),
                    solicitud.get("documento"),
                    solicitud.get("celular"),
                    solicitud.get("correo")
            );
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = servicioUsuario.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable String id) {
        return servicioUsuario.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tipo/{tipoCocinero}")
    public ResponseEntity<List<Usuario>> obtenerUsuariosPorTipo(@PathVariable TipoCocinero tipoCocinero) {
        List<Usuario> usuarios = servicioUsuario.obtenerUsuariosPorTipo(tipoCocinero);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/temporada/{temporada}")
    public ResponseEntity<List<Usuario>> obtenerParticipantesPorTemporada(@PathVariable String temporada) {
        List<Usuario> usuarios = servicioUsuario.obtenerParticipantesPorTemporada(temporada);
        return ResponseEntity.ok(usuarios);
    }
}