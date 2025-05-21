package henrotaym.env.services;

import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.StudioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
  private GameRepository gameRepository;
  private CoverRepository coverRepository;
  private StudioRepository studioRepository;
  private ResourceMapper resourceMapper;

  public GameResource store(GameRequest request) {
    Game game = new Game();

    return this.storeOrUpdate(request, game);
  }

  public GameResource update(BigInteger id, GameRequest request) {
    Game game = this.findById(id);

    return this.storeOrUpdate(request, game);
  }

  public GameResource show(BigInteger id) {
    Game game = this.findById(id);

    return this.resourceMapper.gameResource(game);
  }

  public List<GameResource> index() {
    return this.gameRepository.findAll().stream()
        .map(game -> this.resourceMapper.gameResource(game))
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

  private Cover getCover(GameRequest request) {
    if (request.cover() == null) {
      return null;
    }

    return this.coverRepository.findById(request.cover().id()).get();
  }

  private Studio getStudio(GameRequest request) {
    return this.studioRepository.findById(request.studio().id()).get();
  }

  private GameResource storeOrUpdate(GameRequest request, Game game) {
    game.setCover(this.getCover(request));
    game.setStudio(this.getStudio(request));
    game = this.gameRepository.save(this.resourceMapper.getGameMapper().request(request, game));

    return this.resourceMapper.gameResource(game);
  }
}
