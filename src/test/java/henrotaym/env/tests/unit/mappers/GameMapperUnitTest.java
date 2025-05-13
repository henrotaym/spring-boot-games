package henrotaym.env.tests.unit.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;

public class GameMapperUnitTest {
  @Test
  void it_transforms_a_game_to_a_game_resource() {
    GameMapper gameMapper = new GameMapper();
    Game game = new Game(new BigInteger("1"), ":test", null);

    GameResource gameResource = gameMapper.resource(game);

    assertEquals(game.getId(), gameResource.id());
    assertEquals(game.getName(), gameResource.name());
  }

  @Test
  void it_transforms_game_request_to_game() {
    GameMapper gameMapper = new GameMapper();
    GameRequest gameRequest = new GameRequest(":name");
    Game game = new Game(new BigInteger("1"), ":anotherName", null);

    Game modifiedGame = gameMapper.request(gameRequest, game);

    assertEquals(game.getId(), modifiedGame.getId());
    assertEquals(gameRequest.name(), modifiedGame.getName());
  }
}
