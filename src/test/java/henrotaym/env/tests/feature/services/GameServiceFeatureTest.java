package henrotaym.env.tests.feature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.CoverFactory;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameServiceFeatureTest extends ApplicationTest {
  @Autowired GameRepository gameRepository;
  @Autowired CoverRepository coverRepository;
  @Autowired GameService gameService;
  @Autowired GameFactory gameFactory;
  @Autowired CoverFactory coverFactory;
  @Autowired EntityManager entityManager;

  @Test
  public void it_updates_game_based_on_incoming_game_request() {
    Game expectedGame = this.gameFactory.create(game -> game.setName(":minecraf"));
    Cover expectedCover = this.coverFactory.create();
    String newName = ":roblox";
    CoverRelationshipRequest coverRequest = new CoverRelationshipRequest(expectedCover.getId());
    GameRequest gameRequest = new GameRequest(newName, coverRequest);

    GameResource gameResource = this.gameService.update(expectedGame.getId(), gameRequest);
    entityManager.flush();
    entityManager.clear();

    Game actualGame = this.gameRepository.findById(expectedGame.getId()).get();
    assertEquals(newName, actualGame.getName());
    assertEquals(newName, gameResource.name());
    assertEquals(expectedGame.getId(), gameResource.id());
    assertEquals(expectedCover.getId(), actualGame.getCover().getId());
    assertEquals(expectedCover.getUrl(), actualGame.getCover().getUrl());
    assertEquals(1, this.gameRepository.count());
    assertEquals(1, this.coverRepository.count());
  }

  @Test
  public void it_removes_cover_association_based_on_incoming_game_request() {
    Cover expectedCover = this.coverFactory.create();
    Game expectedGame = this.gameFactory.create(game -> game.setCover(expectedCover));
    CoverRelationshipRequest coverRequest = new CoverRelationshipRequest(null);
    GameRequest gameRequest = new GameRequest(expectedGame.getName(), coverRequest);

    GameResource gameResource = this.gameService.update(expectedGame.getId(), gameRequest);
    entityManager.flush();
    entityManager.clear();

    Game actualGame = this.gameRepository.findById(expectedGame.getId()).get();
    assertEquals(expectedGame.getName(), actualGame.getName());
    assertEquals(expectedGame.getName(), gameResource.name());
    assertEquals(expectedGame.getId(), gameResource.id());
    assertNull(actualGame.getCover());
    assertEquals(1, this.gameRepository.count());
    assertEquals(1, this.coverRepository.count());
  }

  @Test
  public void it_removes_cover_association_based_on_incoming_game_request_with_null_cover() {
    Cover expectedCover = this.coverFactory.create();
    Game expectedGame = this.gameFactory.create(game -> game.setCover(expectedCover));
    // CoverRelationshipRequest coverRequest = new CoverRelationshipRequest(null);
    GameRequest gameRequest = new GameRequest(expectedGame.getName(), null);

    GameResource gameResource = this.gameService.update(expectedGame.getId(), gameRequest);
    entityManager.flush();
    entityManager.clear();

    Game actualGame = this.gameRepository.findById(expectedGame.getId()).get();
    assertEquals(expectedGame.getName(), actualGame.getName());
    assertEquals(expectedGame.getName(), gameResource.name());
    assertEquals(expectedGame.getId(), gameResource.id());
    assertNull(actualGame.getCover());
    assertEquals(1, this.gameRepository.count());
    assertEquals(1, this.coverRepository.count());
  }
}
