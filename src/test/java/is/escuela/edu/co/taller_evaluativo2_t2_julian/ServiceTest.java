package is.escuela.edu.co.taller_evaluativo2_t2_julian;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.*;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.repository.RepositorioReceta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.repository.RepositorioUsuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.service.ServicioReceta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.service.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private RepositorioReceta repositorioReceta;

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private RecetaFactory recetaFactory;

    @Mock
    private UsuarioFactory usuarioFactory;

    @Mock
    private ValidadorUsuario validadorUsuario;

    @Mock
    private ServicioUsuario servicioUsuario;

    @InjectMocks
    private ServicioReceta servicioReceta;

    @InjectMocks
    private ServicioUsuario servicioUsuarioReal;

    private Usuario televidente;
    private Usuario participante;
    private Usuario cocinero;
    private Receta recetaTelevidente;
    private Receta recetaParticipante;
    private Receta recetaCocinero;

    @BeforeEach
    void setUp() {
        televidente = new Usuario();
        televidente.setId("1");
        televidente.setDocumento("123");
        televidente.setCorreo("juan@test.com");
        participante = new Usuario();
        participante.setId("2");
        participante.setDocumento("456");
        participante.setCorreo("maria@test.com");
        cocinero = new Usuario();
        cocinero.setId("3");
        cocinero.setDocumento("789");
        cocinero.setCorreo("pedro@test.com");
        recetaTelevidente = new Receta();
        recetaTelevidente.setId("rec1");
        recetaParticipante = new Receta();
        recetaParticipante.setId("rec2");
        recetaCocinero = new Receta();
        recetaCocinero.setId("rec3");
    }


    @Test
    void registrarTelevidente_UsuarioInvalido_DeberiaLanzarExcepcion() {
        when(usuarioFactory.crearTelevidente("Juan", "123", "555", "juan@test.com"))
                .thenReturn(televidente);
        when(validadorUsuario.validar(televidente)).thenReturn("Usuario inválido");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioUsuarioReal.registrarTelevidente("Juan", "123", "555", "juan@test.com");
        });

        assertEquals("Usuario inválido", exception.getMessage());
        verify(repositorioUsuario, never()).save(any());
    }

    @Test
    void obtenerTodosLosUsuarios_DeberiaRetornarListaUsuarios() {
        List<Usuario> usuarios = Arrays.asList(televidente, participante, cocinero);
        when(repositorioUsuario.findAll()).thenReturn(usuarios);
        List<Usuario> resultado = servicioUsuarioReal.obtenerTodosLosUsuarios();
        assertEquals(3, resultado.size());
        assertEquals(usuarios, resultado);
    }

    @Test
    void obtenerUsuarioPorId_UsuarioExistente_DeberiaRetornarUsuario() {
        when(repositorioUsuario.findById("1")).thenReturn(Optional.of(televidente));
        Optional<Usuario> resultado = servicioUsuarioReal.obtenerUsuarioPorId("1");
        assertTrue(resultado.isPresent());
        assertEquals(televidente, resultado.get());
    }

    @Test
    void obtenerUsuarioPorId_UsuarioNoExistente_DeberiaRetornarVacio() {
        when(repositorioUsuario.findById("99")).thenReturn(Optional.empty());
        Optional<Usuario> resultado = servicioUsuarioReal.obtenerUsuarioPorId("99");
        assertFalse(resultado.isPresent());
    }

    @Test
    void obtenerUsuariosPorTipo_DeberiaRetornarUsuariosFiltrados() {
        List<Usuario> televidentes = Arrays.asList(televidente);
        when(repositorioUsuario.findByTipoCocinero(TipoCocinero.TELEVIDENTE)).thenReturn(televidentes);
        List<Usuario> resultado = servicioUsuarioReal.obtenerUsuariosPorTipo(TipoCocinero.TELEVIDENTE);
        assertEquals(1, resultado.size());
        assertEquals(televidentes, resultado);
    }

    @Test
    void obtenerParticipantesPorTemporada_DeberiaRetornarParticipantes() {
        List<Usuario> participantes = Arrays.asList(participante);
        when(repositorioUsuario.findByTemporada("Temporada1")).thenReturn(participantes);
        List<Usuario> resultado = servicioUsuarioReal.obtenerParticipantesPorTemporada("Temporada1");
        assertEquals(1, resultado.size());
        assertEquals(participantes, resultado);
    }

    @Test
    void registrarRecetaTelevidente_UsuarioExistente_DeberiaGuardarReceta() {
        when(servicioUsuario.obtenerUsuarioPorId("1")).thenReturn(Optional.of(televidente));
        when(recetaFactory.crearRecetaTelevidente("Receta TV", null, null, televidente))
                .thenReturn(recetaTelevidente);
        when(repositorioReceta.save(recetaTelevidente)).thenReturn(recetaTelevidente);
        Receta resultado = servicioReceta.registrarRecetaTelevidente("Receta TV", null, null, "1");
        assertNotNull(resultado);
        assertEquals(recetaTelevidente, resultado);
        verify(repositorioReceta).save(recetaTelevidente);
    }

    @Test
    void registrarRecetaTelevidente_UsuarioNoExistente_DeberiaLanzarExcepcion() {
        when(servicioUsuario.obtenerUsuarioPorId("99")).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.registrarRecetaTelevidente("Receta TV", null, null, "99");
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(repositorioReceta, never()).save(any());
    }

    @Test
    void registrarRecetaParticipante_UsuarioExistente_DeberiaGuardarReceta() {
        when(servicioUsuario.obtenerUsuarioPorId("2")).thenReturn(Optional.of(participante));
        when(recetaFactory.crearRecetaParticipante("Receta Part", null, null, participante, "T1"))
                .thenReturn(recetaParticipante);
        when(repositorioReceta.save(recetaParticipante)).thenReturn(recetaParticipante);
        Receta resultado = servicioReceta.registrarRecetaParticipante("Receta Part", null, null, "2", "T1");
        assertNotNull(resultado);
        assertEquals(recetaParticipante, resultado);
        verify(repositorioReceta).save(recetaParticipante);
    }

    @Test
    void registrarRecetaCocinero_UsuarioExistente_DeberiaGuardarReceta() {
        when(servicioUsuario.obtenerUsuarioPorId("3")).thenReturn(Optional.of(cocinero));
        when(recetaFactory.crearRecetaCocinero("Receta Chef", null, null, cocinero))
                .thenReturn(recetaCocinero);
        when(repositorioReceta.save(recetaCocinero)).thenReturn(recetaCocinero);
        Receta resultado = servicioReceta.registrarRecetaCocinero("Receta Chef", null, null, "3");
        assertNotNull(resultado);
        assertEquals(recetaCocinero, resultado);
        verify(repositorioReceta).save(recetaCocinero);
    }

    @Test
    void obtenerTodasLasRecetas_DeberiaRetornarTodasLasRecetas() {
        List<Receta> recetas = Arrays.asList(recetaTelevidente, recetaParticipante, recetaCocinero);
        when(repositorioReceta.findAll()).thenReturn(recetas);
        List<Receta> resultado = servicioReceta.obtenerTodasLasRecetas();
        assertEquals(3, resultado.size());
        assertEquals(recetas, resultado);
    }

    @Test
    void obtenerRecetaPorConsecutivo_RecetaExistente_DeberiaRetornarReceta() {
        when(repositorioReceta.findByConsecutivo(1)).thenReturn(Optional.of(recetaTelevidente));
        Optional<Receta> resultado = servicioReceta.obtenerRecetaPorConsecutivo(1);
        assertTrue(resultado.isPresent());
        assertEquals(recetaTelevidente, resultado.get());
    }

    @Test
    void obtenerRecetasDeParticipantes_DeberiaRetornarRecetasParticipantes() {
        List<Receta> recetasParticipantes = Arrays.asList(recetaParticipante);
        when(repositorioReceta.findByTipoCocinero(TipoCocinero.PARTICIPANTE)).thenReturn(recetasParticipantes);
        List<Receta> resultado = servicioReceta.obtenerRecetasDeParticipantes();
        assertEquals(1, resultado.size());
        assertEquals(recetasParticipantes, resultado);
    }

    @Test
    void obtenerRecetasDeTelevidentes_DeberiaRetornarRecetasTelevidentes() {
        List<Receta> recetasTelevidentes = Arrays.asList(recetaTelevidente);
        when(repositorioReceta.findByTipoCocinero(TipoCocinero.TELEVIDENTE)).thenReturn(recetasTelevidentes);
        List<Receta> resultado = servicioReceta.obtenerRecetasDeTelevidentes();
        assertEquals(1, resultado.size());
        assertEquals(recetasTelevidentes, resultado);
    }

    @Test
    void obtenerRecetasDeCocineros_DeberiaRetornarRecetasCocineros() {
        List<Receta> recetasCocineros = Arrays.asList(recetaCocinero);
        when(repositorioReceta.findByTipoCocinero(TipoCocinero.COMPETIDOR)).thenReturn(recetasCocineros);
        List<Receta> resultado = servicioReceta.obtenerRecetasDeCocineros();
        assertEquals(1, resultado.size());
        assertEquals(recetasCocineros, resultado);
    }

    @Test
    void obtenerRecetasPorTemporada_DeberiaRetornarRecetasTemporada() {
        List<Receta> recetasTemporada = Arrays.asList(recetaParticipante);
        when(repositorioReceta.findByTemporada("Temporada1")).thenReturn(recetasTemporada);
        List<Receta> resultado = servicioReceta.obtenerRecetasPorTemporada("Temporada1");
        assertEquals(1, resultado.size());
        assertEquals(recetasTemporada, resultado);
    }

    @Test
    void buscarRecetasPorIngrediente_DeberiaRetornarRecetasConIngrediente() {
        List<Receta> recetasConIngrediente = Arrays.asList(recetaTelevidente, recetaParticipante);
        when(repositorioReceta.findByIngredienteContaining("Harina")).thenReturn(recetasConIngrediente);
        List<Receta> resultado = servicioReceta.buscarRecetasPorIngrediente("Harina");
        assertEquals(2, resultado.size());
        assertEquals(recetasConIngrediente, resultado);
    }

    @Test
    void eliminarReceta_RecetaExistente_DeberiaEliminarYRetornarTrue() {
        when(repositorioReceta.existsById("rec1")).thenReturn(true);
        boolean resultado = servicioReceta.eliminarReceta("rec1");
        assertTrue(resultado);
        verify(repositorioReceta).deleteById("rec1");
    }

    @Test
    void eliminarReceta_RecetaNoExistente_DeberiaRetornarFalse() {
        when(repositorioReceta.existsById("rec99")).thenReturn(false);
        boolean resultado = servicioReceta.eliminarReceta("rec99");
        assertFalse(resultado);
        verify(repositorioReceta, never()).deleteById(anyString());
    }

    @Test
    void actualizarReceta_RecetaExistente_DeberiaActualizarYRetornarReceta() {
        Receta recetaActualizada = new Receta();
        recetaActualizada.setTitulo("Nuevo Título");
        when(repositorioReceta.findById("rec1")).thenReturn(Optional.of(recetaTelevidente));
        when(repositorioReceta.save(recetaTelevidente)).thenReturn(recetaTelevidente);
        Optional<Receta> resultado = servicioReceta.actualizarReceta("rec1", recetaActualizada);
        assertTrue(resultado.isPresent());
        assertEquals("Nuevo Título", recetaTelevidente.getTitulo());
        verify(repositorioReceta).save(recetaTelevidente);
    }

    @Test
    void actualizarReceta_RecetaNoExistente_DeberiaRetornarVacio() {
        Receta recetaActualizada = new Receta();
        when(repositorioReceta.findById("rec99")).thenReturn(Optional.empty());
        Optional<Receta> resultado = servicioReceta.actualizarReceta("rec99", recetaActualizada);
        assertFalse(resultado.isPresent());
        verify(repositorioReceta, never()).save(any());
    }

    @Test
    void aprobarReceta_RecetaExistente_DeberiaAprobarYRetornarReceta() {
        when(repositorioReceta.findById("rec1")).thenReturn(Optional.of(recetaTelevidente));
        when(repositorioReceta.save(recetaTelevidente)).thenReturn(recetaTelevidente);
        Optional<Receta> resultado = servicioReceta.aprobarReceta("rec1");
        assertTrue(resultado.isPresent());
        assertTrue(recetaTelevidente.isAprobada());
        verify(repositorioReceta).save(recetaTelevidente);
    }

    @Test
    void obtenerRecetasAprobadas_DeberiaRetornarRecetasAprobadas() {
        List<Receta> recetasAprobadas = Arrays.asList(recetaTelevidente, recetaParticipante);
        when(repositorioReceta.findByAprobada(true)).thenReturn(recetasAprobadas);
        List<Receta> resultado = servicioReceta.obtenerRecetasAprobadas();
        assertEquals(2, resultado.size());
        assertEquals(recetasAprobadas, resultado);
    }

    @Test
    void obtenerRecetasPorCocinero_DeberiaRetornarRecetasDelCocinero() {
        List<Receta> recetasCocinero = Arrays.asList(recetaTelevidente);
        when(repositorioReceta.findByCocineroId("1")).thenReturn(recetasCocinero);
        List<Receta> resultado = servicioReceta.obtenerRecetasPorCocinero("1");
        assertEquals(1, resultado.size());
        assertEquals(recetasCocinero, resultado);
    }
}