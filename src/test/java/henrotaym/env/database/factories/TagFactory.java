package henrotaym.env.database.factories;

import henrotaym.env.entities.Tag;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TagFactory extends EntityFactory<Tag> {

  public TagFactory(
      Faker faker, JpaRepository<Tag, BigInteger> repository, GameFactory gameFactory) {
    super(faker, repository);
  }

  @Override
  protected Tag entity() {
    return new Tag();
  }

  @Override
  protected void attributes(Tag entity) {
    entity.setName(this.faker.videoGame().genre());
  }
}
