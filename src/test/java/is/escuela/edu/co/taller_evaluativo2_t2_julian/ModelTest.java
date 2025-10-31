package is.escuela.edu.co.taller_evaluativo2_t2_julian;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ModelTest {

    @Autowired
    private RecetaFactory recetaFactory;

    @Autowired
    private UsuarioFactory usuarioFactory;

    @Autowired
    private ValidadorReceta validadorReceta;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    private Usuario televidente;
    private Usuario participante;
    private Usuario cocinero;
    private List<Ingrediente> ingredientes;
    private List<String> pasosPreparacion;

    @BeforeEach
    void setUp() {
        televidente = usuarioFactory.crearTelevidente("Juan Perez", "123456", "3001234567", "juan@test.com");
        participante = usuarioFactory.crearParticipante("Maria Lopez", "654321", "3007654321", "maria@test.com", "Temporada1");
        cocinero = usuarioFactory.crearCocineroJurado("Carlos Ruiz", "789012", "3009876543", "carlos@test.com");

        ingredientes = Arrays.asList(
                new Ingrediente("Harina", 500, "gramos"),
                new Ingrediente("Azúcar", 200, "gramos")
        );

        pasosPreparacion = Arrays.asList("Mezclar ingredientes", "Hornear", "Decorar");
    }

    @Test
    void ingrediente_ConstructorVacio_DeberiaCrearInstancia() {
        Ingrediente ingrediente = new Ingrediente();

        assertNotNull(ingrediente);
        assertNull(ingrediente.getNombre());
        assertEquals(0.0, ingrediente.getCantidad());
        assertNull(ingrediente.getUnidad());
    }

    @Test
    void ingrediente_ConstructorConParametros_DeberiaInicializarCorrectamente() {
        Ingrediente ingrediente = new Ingrediente("Harina", 500.0, "gramos");

        assertEquals("Harina", ingrediente.getNombre());
        assertEquals(500.0, ingrediente.getCantidad());
        assertEquals("gramos", ingrediente.getUnidad());
    }

    @Test
    void ingrediente_SettersYGetters_DeberiaFuncionarCorrectamente() {
        Ingrediente ingrediente = new Ingrediente();

        ingrediente.setNombre("Azúcar");
        ingrediente.setCantidad(200.0);
        ingrediente.setUnidad("gramos");

        assertEquals("Azúcar", ingrediente.getNombre());
        assertEquals(200.0, ingrediente.getCantidad());
        assertEquals("gramos", ingrediente.getUnidad());
    }

    @Test
    void usuario_ConstructorVacio_DeberiaCrearInstancia() {
        Usuario usuario = new Usuario();

        assertNotNull(usuario);
        assertNull(usuario.getNombre());
        assertNull(usuario.getDocumento());
        assertNull(usuario.getCelular());
        assertNull(usuario.getCorreo());
        assertNull(usuario.getTipoCocinero());
        assertNull(usuario.getTemporada());
    }

    @Test
    void usuario_SettersYGetters_DeberiaFuncionarCorrectamente() {
        Usuario usuario = new Usuario();

        usuario.setId("1");
        usuario.setNombre("Test User");
        usuario.setDocumento("123456");
        usuario.setCelular("3001234567");
        usuario.setCorreo("test@test.com");
        usuario.setTipoCocinero(TipoCocinero.TELEVIDENTE);
        usuario.setTemporada("Temporada1");

        assertEquals("1", usuario.getId());
        assertEquals("Test User", usuario.getNombre());
        assertEquals("123456", usuario.getDocumento());
        assertEquals("3001234567", usuario.getCelular());
        assertEquals("test@test.com", usuario.getCorreo());
        assertEquals(TipoCocinero.TELEVIDENTE, usuario.getTipoCocinero());
        assertEquals("Temporada1", usuario.getTemporada());
    }

    @Test
    void receta_ConstructorVacio_DeberiaCrearInstanciaConAprobadaFalse() {
        Receta receta = new Receta();

        assertNotNull(receta);
        assertFalse(receta.isAprobada());
        assertNull(receta.getTitulo());
        assertNull(receta.getIngredientes());
        assertNull(receta.getPasosPreparacion());
        assertNull(receta.getCocinero());
        assertNull(receta.getTemporada());
    }

    @Test
    void receta_ConstructorConParametros_DeberiaInicializarCorrectamente() {
        Receta receta = new Receta(1, "Receta Test", ingredientes, pasosPreparacion, televidente, "Temporada1");

        assertEquals(1, receta.getConsecutivo());
        assertEquals("Receta Test", receta.getTitulo());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(pasosPreparacion, receta.getPasosPreparacion());
        assertEquals(televidente, receta.getCocinero());
        assertEquals("Temporada1", receta.getTemporada());
        assertFalse(receta.isAprobada());
    }

    @Test
    void receta_SettersYGetters_DeberiaFuncionarCorrectamente() {
        Receta receta = new Receta();

        receta.setId("rec1");
        receta.setConsecutivo(1);
        receta.setTitulo("Nueva Receta");
        receta.setIngredientes(ingredientes);
        receta.setPasosPreparacion(pasosPreparacion);
        receta.setCocinero(participante);
        receta.setTemporada("Temporada2");
        receta.setAprobada(true);

        assertEquals("rec1", receta.getId());
        assertEquals(1, receta.getConsecutivo());
        assertEquals("Nueva Receta", receta.getTitulo());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(pasosPreparacion, receta.getPasosPreparacion());
        assertEquals(participante, receta.getCocinero());
        assertEquals("Temporada2", receta.getTemporada());
        assertTrue(receta.isAprobada());
    }

    @Test
    void usuarioFactory_crearTelevidente_DeberiaCrearUsuarioCorrecto() {
        Usuario usuario = usuarioFactory.crearTelevidente("Juan", "123", "555", "juan@test.com");

        assertEquals("Juan", usuario.getNombre());
        assertEquals("123", usuario.getDocumento());
        assertEquals("555", usuario.getCelular());
        assertEquals("juan@test.com", usuario.getCorreo());
        assertEquals(TipoCocinero.TELEVIDENTE, usuario.getTipoCocinero());
        assertNull(usuario.getTemporada());
    }

    @Test
    void usuarioFactory_crearParticipante_DeberiaCrearUsuarioCorrecto() {
        Usuario usuario = usuarioFactory.crearParticipante("Maria", "456", "666", "maria@test.com", "T1");

        assertEquals("Maria", usuario.getNombre());
        assertEquals("456", usuario.getDocumento());
        assertEquals("666", usuario.getCelular());
        assertEquals("maria@test.com", usuario.getCorreo());
        assertEquals(TipoCocinero.PARTICIPANTE, usuario.getTipoCocinero());
        assertEquals("T1", usuario.getTemporada());
    }

    @Test
    void usuarioFactory_crearCocineroJurado_DeberiaCrearUsuarioCorrecto() {
        Usuario usuario = usuarioFactory.crearCocineroJurado("Pedro", "789", "777", "pedro@test.com");

        assertEquals("Pedro", usuario.getNombre());
        assertEquals("789", usuario.getDocumento());
        assertEquals("777", usuario.getCelular());
        assertEquals("pedro@test.com", usuario.getCorreo());
        assertEquals(TipoCocinero.COMPETIDOR, usuario.getTipoCocinero());
        assertNull(usuario.getTemporada());
    }

    @Test
    void validadorUsuario_UsuarioValido_DeberiaRetornarUsuarioValido() {
        String resultado = validadorUsuario.validar(televidente);

        assertEquals("Usuario válido", resultado);
    }

    @Test
    void validadorUsuario_UsuarioSinNombre_DeberiaRetornarError() {
        Usuario usuario = new Usuario();
        usuario.setDocumento("123");
        usuario.setCorreo("test@test.com");
        usuario.setTipoCocinero(TipoCocinero.TELEVIDENTE);

        String resultado = validadorUsuario.validar(usuario);

        assertEquals("Error: El nombre es obligatorio", resultado);
    }

    @Test
    void validadorUsuario_UsuarioSinDocumento_DeberiaRetornarError() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setCorreo("test@test.com");
        usuario.setTipoCocinero(TipoCocinero.TELEVIDENTE);

        String resultado = validadorUsuario.validar(usuario);

        assertEquals("Error: El documento es obligatorio", resultado);
    }

    @Test
    void validadorUsuario_UsuarioSinCorreo_DeberiaRetornarError() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setDocumento("123");
        usuario.setTipoCocinero(TipoCocinero.TELEVIDENTE);

        String resultado = validadorUsuario.validar(usuario);

        assertEquals("Error: El correo es obligatorio", resultado);
    }

    @Test
    void validadorUsuario_UsuarioSinTipoCocinero_DeberiaRetornarError() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test");
        usuario.setDocumento("123");
        usuario.setCorreo("test@test.com");

        String resultado = validadorUsuario.validar(usuario);

        assertEquals("Error: El tipo de cocinero es obligatorio", resultado);
    }

    @Test
    void validadorUsuario_ParticipanteSinTemporada_DeberiaRetornarError() {
        Usuario usuario = usuarioFactory.crearParticipante("Test", "123", "555", "test@test.com", null);
        usuario.setTemporada(null);

        String resultado = validadorUsuario.validar(usuario);

        assertEquals("Error: Los participantes deben tener una temporada asignada", resultado);
    }

    @Test
    void validadorReceta_RecetaValida_DeberiaRetornarRecetaValida() {
        Receta receta = new Receta(1, "Receta Test", ingredientes, pasosPreparacion, televidente, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Receta válida", resultado);
    }

    @Test
    void validadorReceta_RecetaSinTitulo_DeberiaRetornarError() {
        Receta receta = new Receta(1, null, ingredientes, pasosPreparacion, televidente, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Error: El título es obligatorio", resultado);
    }

    @Test
    void validadorReceta_RecetaSinIngredientes_DeberiaRetornarError() {
        Receta receta = new Receta(1, "Receta Test", null, pasosPreparacion, televidente, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Error: La receta debe tener al menos un ingrediente", resultado);
    }

    @Test
    void validadorReceta_RecetaSinPasosPreparacion_DeberiaRetornarError() {
        Receta receta = new Receta(1, "Receta Test", ingredientes, null, televidente, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Error: La receta debe tener al menos un paso de preparación", resultado);
    }

    @Test
    void validadorReceta_RecetaSinCocinero_DeberiaRetornarError() {
        Receta receta = new Receta(1, "Receta Test", ingredientes, pasosPreparacion, null, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Error: La receta debe tener un cocinero asignado", resultado);
    }

    @Test
    void validadorReceta_RecetaParticipanteSinTemporada_DeberiaRetornarError() {
        Receta receta = new Receta(1, "Receta Test", ingredientes, pasosPreparacion, participante, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Error: Las recetas de participantes deben tener una temporada asignada", resultado);
    }

    @Test
    void validadorReceta_RecetaCocineroConMenosDe3Pasos_DeberiaRetornarError() {
        List<String> pasosCortos = Arrays.asList("Paso 1", "Paso 2");
        Receta receta = new Receta(1, "Receta Test", ingredientes, pasosCortos, cocinero, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Error: Las recetas de cocineros deben tener al menos 3 pasos de preparación", resultado);
    }

    @Test
    void validadorReceta_RecetaCocineroCon3Pasos_DeberiaSerValida() {
        List<String> pasosCompletos = Arrays.asList("Paso 1", "Paso 2", "Paso 3");
        Receta receta = new Receta(1, "Receta Test", ingredientes, pasosCompletos, cocinero, null);

        String resultado = validadorReceta.validar(receta);

        assertEquals("Receta válida", resultado);
    }

    @Test
    void recetaFactory_crearRecetaTelevidente_DeberiaCrearRecetaCorrecta() {
        Receta receta = recetaFactory.crearRecetaTelevidente("Receta TV", ingredientes, pasosPreparacion, televidente);

        assertEquals("Receta TV", receta.getTitulo());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(pasosPreparacion, receta.getPasosPreparacion());
        assertEquals(televidente, receta.getCocinero());
        assertNull(receta.getTemporada());
        assertFalse(receta.isAprobada());
    }

    @Test
    void recetaFactory_crearRecetaParticipante_DeberiaCrearRecetaCorrecta() {
        Receta receta = recetaFactory.crearRecetaParticipante("Receta Part", ingredientes, pasosPreparacion, participante, "T1");

        assertEquals("Receta Part", receta.getTitulo());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(pasosPreparacion, receta.getPasosPreparacion());
        assertEquals(participante, receta.getCocinero());
        assertEquals("T1", receta.getTemporada());
        assertFalse(receta.isAprobada());
    }

    @Test
    void recetaFactory_crearRecetaCocinero_DeberiaCrearRecetaCorrecta() {
        Receta receta = recetaFactory.crearRecetaCocinero("Receta Chef", ingredientes, pasosPreparacion, cocinero);

        assertEquals("Receta Chef", receta.getTitulo());
        assertEquals(ingredientes, receta.getIngredientes());
        assertEquals(pasosPreparacion, receta.getPasosPreparacion());
        assertEquals(cocinero, receta.getCocinero());
        assertNull(receta.getTemporada());
        assertFalse(receta.isAprobada());
    }

    @Test
    void recetaFactory_crearRecetaInvalida_DeberiaLanzarExcepcion() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            recetaFactory.crearRecetaTelevidente("", ingredientes, pasosPreparacion, televidente);
        });

        assertTrue(exception.getMessage().contains("Error: El título es obligatorio"));
    }

    @Test
    void tipoCocinero_Valores_DeberianExistir() {
        assertEquals(3, TipoCocinero.values().length);
        assertEquals(TipoCocinero.TELEVIDENTE, TipoCocinero.valueOf("TELEVIDENTE"));
        assertEquals(TipoCocinero.PARTICIPANTE, TipoCocinero.valueOf("PARTICIPANTE"));
        assertEquals(TipoCocinero.COMPETIDOR, TipoCocinero.valueOf("COMPETIDOR"));
    }
}