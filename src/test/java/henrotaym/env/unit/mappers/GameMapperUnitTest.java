package henrotaym.env.unit.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;

public class GameMapperUnitTest {
    @Test
    void it_transforms_game_to_a_game_resource() {
        GameMapper gameMapper = new GameMapper();
        Game game = new Game(new BigInteger("1"), ":test");

        GameResource gameResource = gameMapper.resource(game);

        assertEquals(game.getId(), gameResource.id());
        assertEquals(game.getName(), gameResource.name());
    }

    @Test
    void it_transforms_game_request_to_game() {
        GameMapper gameMapper = new GameMapper();
        GameRequest gameRequest = new GameRequest(":name");
        Game game = new Game(new BigInteger("1"), ":anotherName");

        Game modifiedGame = gameMapper.request(gameRequest, game);

        assertEquals(game.getId(), modifiedGame.getId());
        assertEquals(gameRequest.name(), modifiedGame.getName());
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
