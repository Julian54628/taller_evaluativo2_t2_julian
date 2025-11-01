package is.escuela.edu.co.taller_evaluativo2_t2_julian.repository;

import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.Receta;
import is.escuela.edu.co.taller_evaluativo2_t2_julian.Model.TipoCocinero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioReceta extends MongoRepository<Receta, String> {

    @Query("{ 'cocinero.tipoCocinero': ?0 }")
    List<Receta> findByTipoCocinero(TipoCocinero tipoCocinero);

    List<Receta> findByTemporada(String temporada);

    @Query("{ 'ingredientes.nombre': { $regex: ?0, $options: 'i' } }")
    List<Receta> findByIngredienteContaining(String ingrediente);

    Optional<Receta> findByConsecutivo(int consecutivo);

    List<Receta> findByAprobada(boolean aprobada);

    @Query("{ 'cocinero.id': ?0 }")
    List<Receta> findByCocineroId(String cocineroId);
}