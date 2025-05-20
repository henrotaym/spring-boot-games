package henrotaym.env.repositories;

import henrotaym.env.entities.Studio;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, BigInteger> {}
