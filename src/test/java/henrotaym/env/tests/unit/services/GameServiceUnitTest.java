package henrotaym.env.tests.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;
import java.math.BigInteger;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class GameServiceUnitTest {
  @Test
  public void it_stores_a_game_resource_based_on_incoming_request() {
    GameRepository gameRepository = mock(GameRepository.class);
    CoverRepository coverRepository = mock(CoverRepository.class);
    GameMapper gameMapper = mock(GameMapper.class);
    GameService gameService = new GameService(gameRepository, coverRepository, gameMapper);
    GameRequest request = new GameRequest(":name", null);
    Game game = new Game();
    GameResource gameResource = new GameResource(new BigInteger("1"), ":name", null);

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

  @Test
  public void it_updates_a_game_based_on_incoming_request() {
    BigInteger bigInt = BigInteger.valueOf(1);
    GameRequest request = new GameRequest(":name2", null);

    GameRepository gameRepository = mock(GameRepository.class);
    GameMapper gameMapper = mock(GameMapper.class);
    CoverRepository coverRepository = mock(CoverRepository.class);
    GameService gameService = new GameService(gameRepository, coverRepository, gameMapper);

    Game existingGame = new Game(bigInt, ":name", null);
    Game updatedGame = new Game(bigInt, ":name2", null);
    GameResource gameResource = new GameResource(bigInt, ":name3", null);

    when(gameRepository.findById(bigInt)).thenReturn(Optional.of(existingGame));
    when(gameMapper.request(request, existingGame)).thenReturn(existingGame);
    when(gameRepository.save(existingGame)).thenReturn(updatedGame);
    when(gameMapper.resource(updatedGame)).thenReturn(gameResource);

    GameResource actualGameResource = gameService.update(bigInt, request);

    verify(gameRepository).findById(bigInt);
    verify(gameMapper).request(request, existingGame);
    verify(gameRepository).save(existingGame);
    verify(gameMapper).resource(updatedGame);

    assertEquals(gameResource.name(), actualGameResource.name());
    assertEquals(gameResource.id(), actualGameResource.id());
  }
}
