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
    void obtenerTodasLasRecetas_DeberiaRetornarTodasLasRecetas() {
        List<Receta> recetas = Arrays.asList(recetaTelevidente, recetaParticipante, recetaCocinero);
        when(repositorioReceta.findAll()).thenReturn(recetas);

        List<Receta> resultado = servicioReceta.obtenerTodasLasRecetas();

        assertEquals(3, resultado.size());
        assertEquals(recetas, resultado);
    }

    @Test
    void obtenerTodasLasRecetas_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findAll()).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerTodasLasRecetas();
        });

        assertEquals("No se encontraron recetas", exception.getMessage());
    }

    @Test
    void obtenerRecetaPorConsecutivo_RecetaExistente_DeberiaRetornarReceta() {
        when(repositorioReceta.findByConsecutivo(1)).thenReturn(Optional.of(recetaTelevidente));

        Optional<Receta> resultado = servicioReceta.obtenerRecetaPorConsecutivo(1);

        assertTrue(resultado.isPresent());
        assertEquals(recetaTelevidente, resultado.get());
    }

    @Test
    void obtenerRecetaPorConsecutivo_RecetaNoExistente_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByConsecutivo(999)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetaPorConsecutivo(999);
        });

        assertEquals("Receta con consecutivo 999 no encontrada", exception.getMessage());
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
    void obtenerRecetasPorTemporada_TemporadaVacia_DeberiaLanzarExcepcion() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasPorTemporada("");
        });

        assertEquals("La temporada no puede estar vacía", exception.getMessage());
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
    void buscarRecetasPorIngrediente_IngredienteVacio_DeberiaLanzarExcepcion() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.buscarRecetasPorIngrediente("");
        });

        assertEquals("El ingrediente no puede estar vacío", exception.getMessage());
    }

    @Test
    void buscarRecetasPorIngrediente_IngredienteNull_DeberiaLanzarExcepcion() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.buscarRecetasPorIngrediente(null);
        });

        assertEquals("El ingrediente no puede estar vacío", exception.getMessage());
    }

    @Test
    void eliminarReceta_RecetaExistente_DeberiaEliminarYRetornarTrue() {
        when(repositorioReceta.existsById("rec1")).thenReturn(true);

        boolean resultado = servicioReceta.eliminarReceta("rec1");

        assertTrue(resultado);
        verify(repositorioReceta).deleteById("rec1");
    }

    @Test
    void eliminarReceta_RecetaNoExistente_DeberiaLanzarExcepcion() {
        when(repositorioReceta.existsById("rec99")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.eliminarReceta("rec99");
        });

        assertEquals("Receta con ID rec99 no encontrada", exception.getMessage());
        verify(repositorioReceta, never()).deleteById(anyString());
    }

    @Test
    void actualizarReceta_RecetaExistente_DeberiaActualizarYRetornarReceta() {
        Receta recetaActualizada = new Receta();
        recetaActualizada.setTitulo("Nuevo Título");

        when(repositorioReceta.existsById("rec1")).thenReturn(true);
        when(repositorioReceta.findById("rec1")).thenReturn(Optional.of(recetaTelevidente));
        when(repositorioReceta.save(recetaTelevidente)).thenReturn(recetaTelevidente);

        Optional<Receta> resultado = servicioReceta.actualizarReceta("rec1", recetaActualizada);

        assertTrue(resultado.isPresent());
        assertEquals("Nuevo Título", recetaTelevidente.getTitulo());
        verify(repositorioReceta).save(recetaTelevidente);
    }

    @Test
    void actualizarReceta_RecetaNoExistente_DeberiaLanzarExcepcion() {
        Receta recetaActualizada = new Receta();
        when(repositorioReceta.existsById("rec99")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.actualizarReceta("rec99", recetaActualizada);
        });

        assertEquals("Receta con ID rec99 no encontrada", exception.getMessage());
        verify(repositorioReceta, never()).save(any());
    }

    @Test
    void aprobarReceta_RecetaExistente_DeberiaAprobarYRetornarReceta() {
        when(repositorioReceta.existsById("rec1")).thenReturn(true);
        when(repositorioReceta.findById("rec1")).thenReturn(Optional.of(recetaTelevidente));
        when(repositorioReceta.save(recetaTelevidente)).thenReturn(recetaTelevidente);

        Optional<Receta> resultado = servicioReceta.aprobarReceta("rec1");

        assertTrue(resultado.isPresent());
        assertTrue(recetaTelevidente.isAprobada());
        verify(repositorioReceta).save(recetaTelevidente);
    }

    @Test
    void aprobarReceta_RecetaNoExistente_DeberiaLanzarExcepcion() {
        when(repositorioReceta.existsById("rec99")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.aprobarReceta("rec99");
        });

        assertEquals("Receta con ID rec99 no encontrada", exception.getMessage());
        verify(repositorioReceta, never()).save(any());
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

    @Test
    void obtenerRecetasPorCocinero_CocineroSinRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByCocineroId("99")).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasPorCocinero("99");
        });

        assertEquals("No se encontraron recetas para el cocinero con ID: 99", exception.getMessage());
    }

    @Test
    void registrarTelevidente_UsuarioValido_DeberiaCrearUsuario() {
        String nombre = "Juan";
        String documento = "123";
        String celular = "123456789";
        String correo = "juan@test.com";

        when(usuarioFactory.crearTelevidente(nombre, documento, celular, correo)).thenReturn(televidente);
        when(validadorUsuario.validar(televidente)).thenReturn("Usuario válido");
        when(repositorioUsuario.findByDocumento(documento)).thenReturn(Optional.empty());
        when(repositorioUsuario.findByCorreo(correo)).thenReturn(Optional.empty());
        when(repositorioUsuario.save(televidente)).thenReturn(televidente);
        Usuario resultado = servicioUsuarioReal.registrarTelevidente(nombre, documento, celular, correo);

        assertNotNull(resultado);
        assertEquals(televidente, resultado);
        verify(usuarioFactory).crearTelevidente(nombre, documento, celular, correo);
        verify(repositorioUsuario).save(televidente);
    }

    @Test
    void registrarTelevidente_DocumentoExistente_DeberiaLanzarExcepcion() {
        when(usuarioFactory.crearTelevidente(anyString(), anyString(), anyString(), anyString())).thenReturn(televidente);
        when(validadorUsuario.validar(televidente)).thenReturn("Usuario válido");
        when(repositorioUsuario.findByDocumento("123")).thenReturn(Optional.of(televidente));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioUsuarioReal.registrarTelevidente("Juan", "123", "123456789", "juan@test.com");
        });

        assertTrue(exception.getMessage().contains("Ya existe un usuario con el documento"));
    }

    @Test
    void registrarParticipante_UsuarioValido_DeberiaCrearUsuario() {
        String nombre = "Maria";
        String documento = "456";
        String celular = "987654321";
        String correo = "maria@test.com";
        String temporada = "Temporada1";

        when(usuarioFactory.crearParticipante(nombre, documento, celular, correo, temporada)).thenReturn(participante);
        when(validadorUsuario.validar(participante)).thenReturn("Usuario válido");
        when(repositorioUsuario.findByDocumento(documento)).thenReturn(Optional.empty());
        when(repositorioUsuario.findByCorreo(correo)).thenReturn(Optional.empty());
        when(repositorioUsuario.save(participante)).thenReturn(participante);
        Usuario resultado = servicioUsuarioReal.registrarParticipante(nombre, documento, celular, correo, temporada);
        assertNotNull(resultado);
        assertEquals(participante, resultado);
    }

    @Test
    void registrarCocineroJurado_UsuarioValido_DeberiaCrearUsuario() {
        String nombre = "Pedro";
        String documento = "789";
        String celular = "555555555";
        String correo = "pedro@test.com";

        when(usuarioFactory.crearCocineroJurado(nombre, documento, celular, correo)).thenReturn(cocinero);
        when(validadorUsuario.validar(cocinero)).thenReturn("Usuario válido");
        when(repositorioUsuario.findByDocumento(documento)).thenReturn(Optional.empty());
        when(repositorioUsuario.findByCorreo(correo)).thenReturn(Optional.empty());
        when(repositorioUsuario.save(cocinero)).thenReturn(cocinero);

        Usuario resultado = servicioUsuarioReal.registrarCocineroJurado(nombre, documento, celular, correo);

        assertNotNull(resultado);
        assertEquals(cocinero, resultado);
    }

    @Test
    void guardarUsuario_ValidacionFallida_DeberiaLanzarExcepcion() {
        when(usuarioFactory.crearTelevidente(anyString(), anyString(), anyString(), anyString())).thenReturn(televidente);
        when(validadorUsuario.validar(televidente)).thenReturn("Error de validación");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioUsuarioReal.registrarTelevidente("Juan", "123", "123456789", "juan@test.com");
        });

        assertTrue(exception.getMessage().contains("Error de validación"));
    }

    @Test
    void registrarRecetaTelevidente_IngredientesVacios_DeberiaLanzarExcepcion() {
        List<String> pasos = Arrays.asList("Paso 1", "Paso 2");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.registrarRecetaTelevidente("Titulo", Arrays.asList(), pasos, "1");
        });

        assertEquals("Los ingredientes no pueden estar vacíos", exception.getMessage());
    }



    @Test
    void obtenerRecetasDeParticipantes_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByTipoCocinero(TipoCocinero.PARTICIPANTE)).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasDeParticipantes();
        });

        assertEquals("No se encontraron recetas de participantes", exception.getMessage());
    }

    @Test
    void obtenerRecetasDeTelevidentes_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByTipoCocinero(TipoCocinero.TELEVIDENTE)).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasDeTelevidentes();
        });

        assertEquals("No se encontraron recetas de televidentes", exception.getMessage());
    }

    @Test
    void obtenerRecetasDeCocineros_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByTipoCocinero(TipoCocinero.COMPETIDOR)).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasDeCocineros();
        });

        assertEquals("No se encontraron recetas de cocineros", exception.getMessage());
    }

    @Test
    void obtenerRecetasPorTemporada_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByTemporada("Temporada2")).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasPorTemporada("Temporada2");
        });

        assertEquals("No se encontraron recetas para la temporada: Temporada2", exception.getMessage());
    }

    @Test
    void buscarRecetasPorIngrediente_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByIngredienteContaining("Azúcar")).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.buscarRecetasPorIngrediente("Azúcar");
        });

        assertEquals("No se encontraron recetas con el ingrediente: Azúcar", exception.getMessage());
    }

    @Test
    void obtenerRecetasAprobadas_NoHayRecetas_DeberiaLanzarExcepcion() {
        when(repositorioReceta.findByAprobada(true)).thenReturn(Arrays.asList());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasAprobadas();
        });

        assertEquals("No se encontraron recetas aprobadas", exception.getMessage());
    }

    @Test
    void obtenerRecetasPorCocinero_IdVacio_DeberiaLanzarExcepcion() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioReceta.obtenerRecetasPorCocinero("");
        });

        assertEquals("El ID del cocinero no puede estar vacío", exception.getMessage());
    }

}