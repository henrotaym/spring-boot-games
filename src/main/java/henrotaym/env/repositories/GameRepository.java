package henrotaym.env.repositories;

import java.math.BigInteger;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import henrotaym.env.entities.Game;

@Repository
public interface GameRepository extends ListCrudRepository<Game, BigInteger> {
    
}
