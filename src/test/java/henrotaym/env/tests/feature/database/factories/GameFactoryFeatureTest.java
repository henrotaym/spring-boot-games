package henrotaym.env.tests.feature.database.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameFactoryFeatureTest extends ApplicationTest {
  @Autowired private GameRepository gameRepository;

  @Autowired private GameFactory gameFactory;

  @Test
  public void it_makes_a_game_with_fake_data() {
    Game game = this.gameFactory.make();
    assertNotNull(game.getName());
    assertNull(game.getId());
    assertEquals(0, this.gameRepository.count());
  }

  @Test
  public void it_makes_a_game_with_provided_data() {
    String name = "name";
    Game game = this.gameFactory.make(g -> g.setName(name));
    assertEquals(name, game.getName());
    assertNull(game.getId());
    assertEquals(0, this.gameRepository.count());
  }

  @Test
  public void it_creates_a_game_with_fake_data() {
    Game game = this.gameFactory.create();
    assertNotNull(game.getName());
    assertNotNull(game.getId());
    assertEquals(1, this.gameRepository.count());
  }

  @Test
  public void it_creates_a_game_with_provided_data() {
    String name = ":name";
    Game game = this.gameFactory.create(g -> g.setName(name));
    assertEquals(name, game.getName());
    assertNotNull(game.getId());
    assertEquals(1, this.gameRepository.count());
  }
}
