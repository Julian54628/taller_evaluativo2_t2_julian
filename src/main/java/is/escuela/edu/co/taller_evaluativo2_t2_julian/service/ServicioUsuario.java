package is.escuela.edu.co.taller_evaluativo2_t2_julian.service;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Usuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.UsuarioFactory;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.ValidadorUsuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.TipoCocinero;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private UsuarioFactory usuarioFactory;

    @Autowired
    private ValidadorUsuario validadorUsuario;

    public Usuario registrarTelevidente(String nombre, String documento, String celular, String correo) {
        try {
            System.out.println("Registrando televidente: " + nombre);

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new RuntimeException("El nombre es requerido");
            }
            if (documento == null || documento.trim().isEmpty()) {
                throw new RuntimeException("El documento es requerido");
            }
            if (correo == null || correo.trim().isEmpty()) {
                throw new RuntimeException("El correo es requerido");
            }

            Usuario usuario = usuarioFactory.crearTelevidente(nombre, documento, celular, correo);
            return guardarUsuario(usuario);

        } catch (Exception e) {
            System.out.println("Error en registrarTelevidente: " + e.getMessage());
            throw new RuntimeException("Error al registrar televidente: " + e.getMessage());
        }
    }

    public Usuario registrarParticipante(String nombre, String documento, String celular, String correo, String temporada) {
        try {
            System.out.println("Registrando participante: " + nombre);

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new RuntimeException("El nombre es requerido");
            }
            if (documento == null || documento.trim().isEmpty()) {
                throw new RuntimeException("El documento es requerido");
            }
            if (correo == null || correo.trim().isEmpty()) {
                throw new RuntimeException("El correo es requerido");
            }
            if (temporada == null || temporada.trim().isEmpty()) {
                throw new RuntimeException("La temporada es requerida para participantes");
            }

            Usuario usuario = usuarioFactory.crearParticipante(nombre, documento, celular, correo, temporada);
            return guardarUsuario(usuario);

        } catch (Exception e) {
            System.out.println("Error en registrarParticipante: " + e.getMessage());
            throw new RuntimeException("Error al registrar participante: " + e.getMessage());
        }
    }

    public Usuario registrarCocineroJurado(String nombre, String documento, String celular, String correo) {
        try {
            System.out.println("Registrando cocinero: " + nombre);

            if (nombre == null || nombre.trim().isEmpty()) {
                throw new RuntimeException("El nombre es requerido");
            }
            if (documento == null || documento.trim().isEmpty()) {
                throw new RuntimeException("El documento es requerido");
            }
            if (correo == null || correo.trim().isEmpty()) {
                throw new RuntimeException("El correo es requerido");
            }

            Usuario usuario = usuarioFactory.crearCocineroJurado(nombre, documento, celular, correo);
            return guardarUsuario(usuario);

        } catch (Exception e) {
            System.out.println("Error en registrarCocineroJurado: " + e.getMessage());
            throw new RuntimeException("Error al registrar cocinero: " + e.getMessage());
        }
    }

    private Usuario guardarUsuario(Usuario usuario) {
        try {
            String resultadoValidacion = validadorUsuario.validar(usuario);
            if (!resultadoValidacion.equals("Usuario válido")) {
                throw new RuntimeException(resultadoValidacion);
            }

            if (repositorioUsuario.findByDocumento(usuario.getDocumento()).isPresent()) {
                throw new RuntimeException("Ya existe un usuario con el documento: " + usuario.getDocumento());
            }
            if (repositorioUsuario.findByCorreo(usuario.getCorreo()).isPresent()) {
                throw new RuntimeException("Ya existe un usuario con el correo: " + usuario.getCorreo());
            }

            return repositorioUsuario.save(usuario);

        } catch (Exception e) {
            System.out.println("Error en guardarUsuario: " + e.getMessage());
            throw new RuntimeException("Error al guardar usuario: " + e.getMessage());
        }
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