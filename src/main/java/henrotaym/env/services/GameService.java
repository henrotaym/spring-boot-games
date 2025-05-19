package henrotaym.env.services;

import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

  private final CoverRepository coverRepository;
  private final GameRepository gameRepository;
  private final GameMapper gameMapper;

  public GameResource store(GameRequest request) {
    return this.storeOrUpdate(request, new Game());
  }

  public GameResource update(BigInteger id, GameRequest request) {
    Game game = this.findById(id);

    return this.storeOrUpdate(request, game);
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

  private Cover getCover(GameRequest request, Game game) {
    if (request.getCover() == null) {
      return null;
    }

    return Optional.ofNullable(request.getCover().id())
        .map(id -> this.coverRepository.findById(id).get())
        .orElse(null);
  }

  private GameResource storeOrUpdate(GameRequest request, Game game) {
    game = this.gameMapper.request(request, game);
    Cover cover = this.getCover(request, game);
    game.setCover(cover);
    game = this.gameRepository.save(game);

    return this.gameMapper.resource(game);
  }
}
