package henrotaym.env.database.factories;

import henrotaym.env.entities.Cover;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CoverFactory extends EntityFactory<Cover> {

  public CoverFactory(Faker faker, JpaRepository<Cover, BigInteger> repository) {
    super(faker, repository);
  }

  @Override
  protected Cover entity() {
    return new Cover();
  }

  @Override
  protected void attributes(Cover entity) {
    entity.setUrl(this.faker.internet().url());
  }
}
