package henrotaym.env.repositories;

import henrotaym.env.entities.Tag;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, BigInteger> {}
