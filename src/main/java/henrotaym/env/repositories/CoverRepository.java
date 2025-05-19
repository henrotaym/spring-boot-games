package henrotaym.env.repositories;

import henrotaym.env.entities.Cover;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverRepository extends JpaRepository<Cover, BigInteger> {}
