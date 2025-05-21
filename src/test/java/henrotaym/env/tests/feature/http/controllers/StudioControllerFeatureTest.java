package henrotaym.env.tests.feature.http.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.requests.relationships.GameRelationshipRequest;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.StudioRepository;
import henrotaym.env.utils.api.JsonClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudioControllerFeatureTest extends ApplicationTest {

  @Autowired StudioRepository studioRepository;

  @Autowired GameFactory gameFactory;
  @Autowired GameMapper gameMapper;

  @Autowired JsonClient jsonClient;

  @Test
  public void it_responds_to_store_url_with_games() throws Exception {
    Game game = this.gameFactory.create();
    String name = "test";
    List<GameRelationshipRequest> games = new ArrayList<GameRelationshipRequest>();
    games.add(this.gameMapper.relationshipRequest(game));
    StudioRequest studioRequest = new StudioRequest(name, games);

    this.jsonClient
        .request(request -> request.post("/studios").content(studioRequest))
        .perform()
        .content("$.name", content -> content.value(name))
        .status(status -> status.isCreated())
        .print();

    assertEquals(2, this.studioRepository.count());
  }
}
