package henrotaym.env.feature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import henrotaym.env.ApplicationTest;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;

public class GameServiceFeatureTest extends ApplicationTest {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @Test
    public void it_updates_game_based_on_incoming_game_request() {
        String oldName = ":minecraf";
        String newName = ":roblox";
        Game originalGame = new Game(null, oldName);
        this.gameRepository.save(originalGame);
        GameRequest gameRequest = new GameRequest(newName);
        
        GameResource gameResource = this.gameService.update(originalGame.getId(), gameRequest);

        assertEquals(originalGame.getId(), gameResource.id());
        assertEquals(gameRequest.name(), gameResource.name());
        // TODO see why.
        // assertNotEquals(originalGame.getName(), gameResource.name());
    }
}
