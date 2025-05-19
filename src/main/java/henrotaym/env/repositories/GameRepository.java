package henrotaym.env.repositories;

import henrotaym.env.entities.Game;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, BigInteger> {}
