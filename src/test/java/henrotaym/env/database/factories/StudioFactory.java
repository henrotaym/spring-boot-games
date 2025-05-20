package henrotaym.env.database.factories;

import henrotaym.env.entities.Studio;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class StudioFactory extends EntityFactory<Studio> {

  public StudioFactory(Faker faker, JpaRepository<Studio, BigInteger> repository) {
    super(faker, repository);
  }

  @Override
  protected Studio entity() {
    return new Studio();
  }

  @Override
  protected void attributes(Studio entity) {
    entity.setName(this.faker.computer().brand());
  }
}
