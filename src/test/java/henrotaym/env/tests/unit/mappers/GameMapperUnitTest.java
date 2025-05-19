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
    Game game = new Game();
    game.setId(new BigInteger("1"));
    game.setName(":test");

    GameResource gameResource = gameMapper.resource(game);

    assertEquals(game.getId(), gameResource.id());
    assertEquals(game.getName(), gameResource.name());
  }

  @Test
  void it_transforms_game_request_to_game() {
    GameMapper gameMapper = new GameMapper();
    GameRequest gameRequest = new GameRequest(":name", null);
    Game game = new Game();
    game.setId(new BigInteger("1"));
    game.setName(":anotherName");

    Game modifiedGame = gameMapper.request(gameRequest, game);

    assertEquals(game.getId(), modifiedGame.getId());
    assertEquals(gameRequest.getName(), modifiedGame.getName());
  }

  // @Test
  // void it_transforms_an_api_exception_to_a_game()
  // {
  //     Apple apple = new Apple(100);
  //     AppleMapper appleMapper = new AppleMapper();
  //     Compote compote = appleMapper.compote(apple);

  //     assertEquals(apple.weight() / 2, compote.weight());
  // }
}
