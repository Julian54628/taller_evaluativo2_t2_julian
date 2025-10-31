package is.escuela.edu.co.taller_evaluativo2_t2_julian.service;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.Usuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.FabricaUsuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.ValidadorUsuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.TipoCocinero;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private FabricaUsuario fabricaUsuario;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    public Usuario registrarTelevidente(String nombre, String documento, String celular, String correo) {
        Usuario usuario = fabricaUsuario.crearTelevidente(nombre, documento, celular, correo);
        return guardarUsuario(usuario);
    }

    public Usuario registrarParticipante(String nombre, String documento, String celular, String correo, String temporada) {
        Usuario usuario = fabricaUsuario.crearParticipante(nombre, documento, celular, correo, temporada);
        return guardarUsuario(usuario);
    }

    public Usuario registrarCocineroJurado(String nombre, String documento, String celular, String correo) {
        Usuario usuario = fabricaUsuario.crearCocineroJurado(nombre, documento, celular, correo);
        return guardarUsuario(usuario);
    }

    private Usuario guardarUsuario(Usuario usuario) {
        String resultadoValidacion = validadorUsuario.validar(usuario);
        if (!resultadoValidacion.equals("Usuario válido")) {
            throw new RuntimeException(resultadoValidacion);
        }

        if (repositorioUsuario.findByDocumento(usuario.getDocumento()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con este documento");
        }
        if (repositorioUsuario.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con este correo");
        }

        return repositorioUsuario.save(usuario);
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return repositorioUsuario.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(String id) {
        return repositorioUsuario.findById(id);
    }

    public List<Usuario> obtenerUsuariosPorTipo(TipoCocinero tipoCocinero) {
        return repositorioUsuario.findByTipoCocinero(tipoCocinero);
    }

    public List<Usuario> obtenerParticipantesPorTemporada(String temporada) {
        return repositorioUsuario.findByTemporada(temporada);
    }
}