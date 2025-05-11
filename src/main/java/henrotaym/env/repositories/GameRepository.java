package henrotaym.env.repositories;

import henrotaym.env.entities.Game;
import java.math.BigInteger;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends ListCrudRepository<Game, BigInteger> {}
