package henrotaym.env.database.factories;

import henrotaym.env.entities.Game;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GameFactory extends EntityFactory<Game> {

  public GameFactory(Faker faker, JpaRepository<Game, BigInteger> repository) {
    super(faker, repository);
  }

  @Override
  protected Game entity() {
    return new Game();
  }

  @Override
  protected void definitions(Game entity) {
    entity.setName(this.faker.naruto().character());
  }
}
