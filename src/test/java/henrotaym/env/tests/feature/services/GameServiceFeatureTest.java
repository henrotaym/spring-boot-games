package henrotaym.env.tests.feature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.database.factories.StudioFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.requests.relationships.StudioRelationshipRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceFeatureTest extends ApplicationTest {
  @Autowired GameRepository gameRepository;
  @Autowired GameService gameService;
  @Autowired GameFactory gameFactory;
  @Autowired StudioFactory studioFactory;
  @Autowired EntityManager entityManager;

  @Test
  public void it_updates_game_based_on_incoming_game_request() {
    // new GameResource(BigInteger.ONE, "sdf")
    Game originalGame = this.gameFactory.create();
    Studio studio = this.studioFactory.create();

    String newName = ":roblox";
    GameRequest gameRequest =
        new GameRequest(newName, null, new StudioRelationshipRequest(studio.getId()), List.of());
    GameResource gameResource = this.gameService.update(originalGame.getId(), gameRequest);

    entityManager.flush();
    entityManager.clear();

    assertEquals(originalGame.getId(), gameResource.getId());
    assertEquals(gameRequest.name(), gameResource.getName());
    assertEquals(originalGame.getStudio().getId(), studio.getId());
  }
}
