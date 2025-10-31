package is.escuela.edu.co.taller_evaluativo2_t2_julian.repository;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.Usuario;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Modelo.TipoCocinero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioUsuario extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByDocumento(String documento);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByTipoCocinero(TipoCocinero tipoCocinero);
    List<Usuario> findByTemporada(String temporada);
}