package henrotaym.env.database.factories;

import henrotaym.env.entities.Game;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class GameFactory extends EntityFactory<Game> {
  private StudioFactory studioFactory;

  public GameFactory(
      Faker faker, JpaRepository<Game, BigInteger> repository, StudioFactory studioFactory) {
    super(faker, repository);
    this.studioFactory = studioFactory;
  }

  @Override
  protected Game entity() {
    return new Game();
  }

  @Override
  protected void attributes(Game entity) {
    entity.setName(this.faker.videoGame().title());
  }

  @Override
  protected void relationships(Game entity) {
    if (entity.getStudio() == null) {
      entity.setStudio(this.studioFactory.create());
    }
  }
}
