package henrotaym.env.services;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
  private GameRepository gameRepository;
  private GameMapper gameMapper;

  public GameResource store(GameRequest request) {
    Game game = new Game();
    game = this.gameRepository.save(this.gameMapper.request(request, game));

    return this.gameMapper.resource(game);
  }

  public GameResource update(BigInteger id, GameRequest request) {
    Game game = this.findById(id);

    game = this.gameRepository.save(this.gameMapper.request(request, game));

    return this.gameMapper.resource(game);
  }

  public GameResource show(BigInteger id) {
    Game game = this.findById(id);

    return this.gameMapper.resource(game);
  }

  public List<GameResource> index() {
    return this.gameRepository.findAll().stream()
        .map(game -> this.gameMapper.resource(game))
        .toList();
  }

  public void destroy(BigInteger id) {
    Game game = this.findById(id);

    this.gameRepository.delete(game);
  }

  private Game findById(BigInteger id) {
    return this.gameRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Game not found."));
  }
}
