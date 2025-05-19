package henrotaym.env.tests.feature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceFeatureTest extends ApplicationTest {
  @Autowired GameRepository gameRepository;
  @Autowired GameService gameService;
  @Autowired GameFactory gameFactory;

  @Test
  public void it_updates_game_based_on_incoming_game_request() {
    Game originalGame = this.gameFactory.create(game -> game.setName(":minecraf"));

    String newName = ":roblox";
    GameRequest gameRequest = new GameRequest(newName, null);
    GameResource gameResource = this.gameService.update(originalGame.getId(), gameRequest);

    assertEquals(originalGame.getId(), gameResource.id());
    assertEquals(gameRequest.name(), gameResource.name());
  }
}
