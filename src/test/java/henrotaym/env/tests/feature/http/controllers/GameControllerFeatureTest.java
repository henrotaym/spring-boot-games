package henrotaym.env.tests.feature.http.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.utils.api.JsonClient;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class GameControllerFeatureTest extends ApplicationTest {
  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @Autowired GameRepository gameRepository;

  @Autowired GameFactory gameFactory;

  @Autowired JsonClient jsonClient;

  @Test
  public void it_responds_to_store_url() throws Exception {
    String name = "test";
    GameRequest body = new GameRequest(name, null);

    this.jsonClient
        .request(request -> request.post("/games").content(body))
        .perform()
        .content("$.name", content -> content.value(name))
        .status(status -> status.isCreated());

    assertEquals(1, this.gameRepository.count());
  }

  @Test
  public void it_responds_to_update_url() throws Exception {
    Game game = this.gameFactory.create();
    String newName = "roblox";
    GameRequest body = new GameRequest(newName, null);

    this.jsonClient
        .request(request -> request.put("/games/{id}", game.getId()).content(body))
        .perform()
        .content("$.name", content -> content.value(newName))
        .status(status -> status.isOk());

    Game updatedGame = this.gameRepository.findById(game.getId()).get();
    assertEquals(newName, updatedGame.getName());
    assertEquals(1, this.gameRepository.count());
  }

  @Test
  public void it_responds_to_update_not_found_related_cover_url() throws Exception {
    Game game = this.gameFactory.create();
    String newName = "roblox";
    GameRequest body =
        new GameRequest(newName, new CoverRelationshipRequest(BigInteger.valueOf(50000)));

    this.jsonClient
        .request(request -> request.put("/games/{id}", game.getId()).content(body))
        .perform()
        .content("$.data.['cover.id']", content -> content.value("Entity not found."))
        .status(status -> status.isBadRequest());

    Game updatedGame = this.gameRepository.findById(game.getId()).get();
    assertEquals(game.getName(), updatedGame.getName());
    assertEquals(1, this.gameRepository.count());
  }
}
