package henrotaym.env.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;

public class GameServiceUnitTest {
    @Test
    public void it_stores_a_game_resource_based_on_incoming_request() 
    {
        GameRepository gameRepository = mock(GameRepository.class);
        GameMapper gameMapper = mock(GameMapper.class);
        GameService gameService = new GameService(gameRepository, gameMapper);
        GameRequest request = new GameRequest(":name");
        Game game = new Game();
        GameResource gameResource = new GameResource(new BigInteger("1"), ":name");

        when(gameMapper.request(eq(request), any(Game.class))).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);
        when(gameMapper.resource(game)).thenReturn(gameResource);

        GameResource actualGameResource = gameService.store(request);

        verify(gameMapper).request(eq(request), any(Game.class));
        verify(gameRepository).save(game);
        verify(gameMapper).resource(game);

        assertEquals(request.name(), actualGameResource.name());
        assertEquals(gameResource.name(), actualGameResource.name());
        assertEquals(gameResource.id(), actualGameResource.id());
    }
}
