package is.escuela.edu.co.taller_evaluativo2_t2_julian;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller.ControladorReceta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Controller.ControladorUsuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.*;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.service.ServicioReceta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.service.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerTest {

    @Mock
    private ServicioUsuario servicioUsuario;

    @Mock
    private ServicioReceta servicioReceta;

    @InjectMocks
    private ControladorUsuario controladorUsuario;

    @InjectMocks
    private ControladorReceta controladorReceta;

    private Usuario usuarioTelevidente;
    private Usuario usuarioParticipante;
    private Usuario usuarioCocinero;
    private Receta recetaTelevidente;
    private Receta recetaParticipante;
    private Receta recetaCocinero;
    private List<Ingrediente> ingredientes;
    private List<String> pasosPreparacion;

    @BeforeEach
    void setUp() {
        usuarioTelevidente = new Usuario("Juan Perez", "12345678", "3001234567", "juan@email.com", TipoCocinero.TELEVIDENTE, null);
        usuarioParticipante = new Usuario("Maria Lopez", "87654321", "3007654321", "maria@email.com", TipoCocinero.PARTICIPANTE, "Temporada 1");
        usuarioCocinero = new Usuario("Carlos Chef", "11223344", "3001122334", "carlos@email.com", TipoCocinero.COMPETIDOR, null);
        ingredientes = Arrays.asList(
                new Ingrediente("Harina", 200, "gramos"),
                new Ingrediente("Azúcar", 100, "gramos"),
                new Ingrediente("Huevos", 2, "unidades")
        );
        pasosPreparacion = Arrays.asList("Mezclar ingredientes", "Hornear a 180°C", "Decorar y servir");
        recetaTelevidente = new Receta(1, "Torta de Chocolate", ingredientes, pasosPreparacion, usuarioTelevidente, null);
        recetaParticipante = new Receta(2, "Pasta Alfredo", ingredientes, pasosPreparacion, usuarioParticipante, "Temporada 1");
        recetaCocinero = new Receta(3, "Risotto de Champiñones", ingredientes, pasosPreparacion, usuarioCocinero, null);
    }


    @Test
    void testRegistrarTelevidenteExitoso() {
        Map<String, String> solicitud = new HashMap<>();
        solicitud.put("nombre", "Juan Perez");
        solicitud.put("documento", "12345678");
        solicitud.put("celular", "3001234567");
        solicitud.put("correo", "juan@email.com");
        when(servicioUsuario.registrarTelevidente(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(usuarioTelevidente);

        ResponseEntity<Usuario> respuesta = controladorUsuario.registrarTelevidente(solicitud);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Juan Perez", respuesta.getBody().getNombre());
        assertEquals(TipoCocinero.TELEVIDENTE, respuesta.getBody().getTipoCocinero());
    }

    @Test
    void testRegistrarTelevidenteConError() {
        Map<String, String> solicitud = new HashMap<>();
        solicitud.put("nombre", "Juan Perez");
        when(servicioUsuario.registrarTelevidente(anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Error de validación"));

        ResponseEntity<Usuario> respuesta = controladorUsuario.registrarTelevidente(solicitud);
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    @Test
    void testRegistrarParticipanteExitoso() {
        Map<String, String> solicitud = new HashMap<>();
        solicitud.put("nombre", "Maria Lopez");
        solicitud.put("documento", "87654321");
        solicitud.put("celular", "3007654321");
        solicitud.put("correo", "maria@email.com");
        solicitud.put("temporada", "Temporada 1");
        when(servicioUsuario.registrarParticipante(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(usuarioParticipante);

        ResponseEntity<Usuario> respuesta = controladorUsuario.registrarParticipante(solicitud);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Maria Lopez", respuesta.getBody().getNombre());
        assertEquals("Temporada 1", respuesta.getBody().getTemporada());
    }

    @Test
    void testRegistrarCocineroJuradoExitoso() {
        Map<String, String> solicitud = new HashMap<>();
        solicitud.put("nombre", "Carlos Chef");
        solicitud.put("documento", "11223344");
        solicitud.put("celular", "3001122334");
        solicitud.put("correo", "carlos@email.com");
        when(servicioUsuario.registrarCocineroJurado(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(usuarioCocinero);
        ResponseEntity<Usuario> respuesta = controladorUsuario.registrarCocineroJurado(solicitud);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals(TipoCocinero.COMPETIDOR, respuesta.getBody().getTipoCocinero());
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        List<Usuario> usuarios = Arrays.asList(usuarioTelevidente, usuarioParticipante, usuarioCocinero);
        when(servicioUsuario.obtenerTodosLosUsuarios()).thenReturn(usuarios);
        ResponseEntity<List<Usuario>> respuesta = controladorUsuario.obtenerTodosLosUsuarios();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(3, respuesta.getBody().size());
    }

    @Test
    void testObtenerUsuarioPorIdExistente() {
        String usuarioId = "123";
        when(servicioUsuario.obtenerUsuarioPorId(usuarioId)).thenReturn(Optional.of(usuarioTelevidente));
        ResponseEntity<Usuario> respuesta = controladorUsuario.obtenerUsuarioPorId(usuarioId);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void testObtenerUsuarioPorIdNoExistente() {
        String usuarioId = "999";
        when(servicioUsuario.obtenerUsuarioPorId(usuarioId)).thenReturn(Optional.empty());
        ResponseEntity<Usuario> respuesta = controladorUsuario.obtenerUsuarioPorId(usuarioId);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testObtenerUsuariosPorTipo() {
        List<Usuario> televidentes = Arrays.asList(usuarioTelevidente);
        when(servicioUsuario.obtenerUsuariosPorTipo(TipoCocinero.TELEVIDENTE)).thenReturn(televidentes);
        ResponseEntity<List<Usuario>> respuesta = controladorUsuario.obtenerUsuariosPorTipo(TipoCocinero.TELEVIDENTE);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void testObtenerParticipantesPorTemporada() {
        List<Usuario> participantes = Arrays.asList(usuarioParticipante);
        when(servicioUsuario.obtenerParticipantesPorTemporada("Temporada 1")).thenReturn(participantes);
        ResponseEntity<List<Usuario>> respuesta = controladorUsuario.obtenerParticipantesPorTemporada("Temporada 1");
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }




    @Test
    void testObtenerTodasLasRecetas() {
        List<Receta> recetas = Arrays.asList(recetaTelevidente, recetaParticipante, recetaCocinero);
        when(servicioReceta.obtenerTodasLasRecetas()).thenReturn(recetas);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerTodasLasRecetas();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(3, respuesta.getBody().size());
    }

    @Test
    void testObtenerRecetaPorConsecutivoExistente() {
        int consecutivo = 1;
        when(servicioReceta.obtenerRecetaPorConsecutivo(consecutivo)).thenReturn(Optional.of(recetaTelevidente));
        ResponseEntity<Receta> respuesta = controladorReceta.obtenerRecetaPorConsecutivo(consecutivo);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void testObtenerRecetaPorConsecutivoNoExistente() {
        int consecutivo = 999;
        when(servicioReceta.obtenerRecetaPorConsecutivo(consecutivo)).thenReturn(Optional.empty());
        ResponseEntity<Receta> respuesta = controladorReceta.obtenerRecetaPorConsecutivo(consecutivo);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testObtenerRecetasDeParticipantes() {
        List<Receta> recetasParticipantes = Arrays.asList(recetaParticipante);
        when(servicioReceta.obtenerRecetasDeParticipantes()).thenReturn(recetasParticipantes);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerRecetasDeParticipantes();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void testObtenerRecetasDeTelevidentes() {
        List<Receta> recetasTelevidentes = Arrays.asList(recetaTelevidente);
        when(servicioReceta.obtenerRecetasDeTelevidentes()).thenReturn(recetasTelevidentes);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerRecetasDeTelevidentes();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void testObtenerRecetasDeCocineros() {
        List<Receta> recetasCocineros = Arrays.asList(recetaCocinero);
        when(servicioReceta.obtenerRecetasDeCocineros()).thenReturn(recetasCocineros);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerRecetasDeCocineros();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void testObtenerRecetasPorTemporada() {
        List<Receta> recetasTemporada = Arrays.asList(recetaParticipante);
        when(servicioReceta.obtenerRecetasPorTemporada("Temporada 1")).thenReturn(recetasTemporada);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerRecetasPorTemporada("Temporada 1");
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void testBuscarRecetasPorIngrediente() {
        List<Receta> recetasConHarina = Arrays.asList(recetaTelevidente, recetaParticipante);
        when(servicioReceta.buscarRecetasPorIngrediente("Harina")).thenReturn(recetasConHarina);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.buscarRecetasPorIngrediente("Harina");
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, respuesta.getBody().size());
    }

    @Test
    void testEliminarRecetaExistente() {
        String recetaId = "rec123";
        when(servicioReceta.eliminarReceta(recetaId)).thenReturn(true);
        ResponseEntity<Void> respuesta = controladorReceta.eliminarReceta(recetaId);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void testEliminarRecetaNoExistente() {
        String recetaId = "rec999";
        when(servicioReceta.eliminarReceta(recetaId)).thenReturn(false);
        ResponseEntity<Void> respuesta = controladorReceta.eliminarReceta(recetaId);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testActualizarRecetaExistente() {
        String recetaId = "rec123";
        Receta recetaActualizada = new Receta(1, "Torta Actualizada", ingredientes, pasosPreparacion, usuarioTelevidente, null);
        when(servicioReceta.actualizarReceta(recetaId, recetaActualizada)).thenReturn(Optional.of(recetaActualizada));
        ResponseEntity<Receta> respuesta = controladorReceta.actualizarReceta(recetaId, recetaActualizada);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void testActualizarRecetaNoExistente() {
        String recetaId = "rec999";
        Receta recetaActualizada = new Receta(1, "Torta Actualizada", ingredientes, pasosPreparacion, usuarioTelevidente, null);
        when(servicioReceta.actualizarReceta(recetaId, recetaActualizada)).thenReturn(Optional.empty());
        ResponseEntity<Receta> respuesta = controladorReceta.actualizarReceta(recetaId, recetaActualizada);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testAprobarRecetaExistente() {
        String recetaId = "rec123";
        recetaTelevidente.setAprobada(true);
        when(servicioReceta.aprobarReceta(recetaId)).thenReturn(Optional.of(recetaTelevidente));
        ResponseEntity<Receta> respuesta = controladorReceta.aprobarReceta(recetaId);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertTrue(respuesta.getBody().isAprobada());
    }

    @Test
    void testAprobarRecetaNoExistente() {
        String recetaId = "rec999";
        when(servicioReceta.aprobarReceta(recetaId)).thenReturn(Optional.empty());
        ResponseEntity<Receta> respuesta = controladorReceta.aprobarReceta(recetaId);
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void testObtenerRecetasAprobadas() {
        recetaTelevidente.setAprobada(true);
        List<Receta> recetasAprobadas = Arrays.asList(recetaTelevidente);
        when(servicioReceta.obtenerRecetasAprobadas()).thenReturn(recetasAprobadas);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerRecetasAprobadas();
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertTrue(respuesta.getBody().get(0).isAprobada());
    }

    @Test
    void testObtenerRecetasPorCocinero() {
        String cocineroId = "cocinero123";
        List<Receta> recetasCocinero = Arrays.asList(recetaTelevidente);
        when(servicioReceta.obtenerRecetasPorCocinero(cocineroId)).thenReturn(recetasCocinero);
        ResponseEntity<List<Receta>> respuesta = controladorReceta.obtenerRecetasPorCocinero(cocineroId);
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void testValidarCastEnRegistrarReceta() {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("titulo", "Receta Test");
        solicitud.put("ingredientes", "esto debería ser una lista");
        solicitud.put("pasosPreparacion", Arrays.asList("Paso 1", "Paso 2"));
        assertDoesNotThrow(() -> {
            controladorReceta.registrarRecetaTelevidente(solicitud, "user123");
        });
    }
    @Test
    void testRegistrarParticipanteConError() {
        Map<String, String> solicitud = new HashMap<>();
        solicitud.put("nombre", "Maria Lopez");
        when(servicioUsuario.registrarParticipante(anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Error de validación"));

        ResponseEntity<Usuario> respuesta = controladorUsuario.registrarParticipante(solicitud);
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    @Test
    void testRegistrarCocineroJuradoConError() {
        Map<String, String> solicitud = new HashMap<>();
        solicitud.put("nombre", "Carlos Chef");
        when(servicioUsuario.registrarCocineroJurado(anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Error de validación"));

        ResponseEntity<Usuario> respuesta = controladorUsuario.registrarCocineroJurado(solicitud);
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }
    @Test
    void testRegistrarRecetaParticipanteExitoso() {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("titulo", "Pasta Alfredo");
        solicitud.put("ingredientes", ingredientes);
        solicitud.put("pasosPreparacion", pasosPreparacion);
        solicitud.put("temporada", "Temporada 1");

        when(servicioReceta.registrarRecetaParticipante(anyString(), anyList(), anyList(), anyString(), anyString()))
                .thenReturn(recetaParticipante);

        ResponseEntity<?> respuesta = controladorReceta.registrarRecetaParticipante(solicitud, "user456");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Pasta Alfredo", ((Receta) respuesta.getBody()).getTitulo());
    }

    @Test
    void testRegistrarRecetaParticipanteConError() {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("titulo", "Pasta Alfredo");
        solicitud.put("ingredientes", ingredientes);
        solicitud.put("pasosPreparacion", pasosPreparacion);
        solicitud.put("temporada", "Temporada 1");

        when(servicioReceta.registrarRecetaParticipante(anyString(), anyList(), anyList(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Error al registrar receta participante"));

        ResponseEntity<?> respuesta = controladorReceta.registrarRecetaParticipante(solicitud, "user456");

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Error al registrar receta participante", respuesta.getBody());
    }

    @Test
    void testRegistrarRecetaCocineroExitoso() {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("titulo", "Risotto de Champiñones");
        solicitud.put("ingredientes", ingredientes);
        solicitud.put("pasosPreparacion", pasosPreparacion);

        when(servicioReceta.registrarRecetaCocinero(anyString(), anyList(), anyList(), anyString()))
                .thenReturn(recetaCocinero);

        ResponseEntity<?> respuesta = controladorReceta.registrarRecetaCocinero(solicitud, "user789");

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Risotto de Champiñones", ((Receta) respuesta.getBody()).getTitulo());
    }

    @Test
    void testRegistrarRecetaCocineroConError() {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("titulo", "Risotto de Champiñones");
        solicitud.put("ingredientes", ingredientes);
        solicitud.put("pasosPreparacion", pasosPreparacion);

        when(servicioReceta.registrarRecetaCocinero(anyString(), anyList(), anyList(), anyString()))
                .thenThrow(new RuntimeException("Error al registrar receta cocinero"));

        ResponseEntity<?> respuesta = controladorReceta.registrarRecetaCocinero(solicitud, "user789");

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("Error al registrar receta cocinero", respuesta.getBody());
    }
}