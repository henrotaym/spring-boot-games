package henrotaym.env.services;

import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.StudioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
  private GameRepository gameRepository;
  private CoverRepository coverRepository;
  private StudioRepository studioRepository;
  private GameMapper gameMapper;

  public Game store(GameRequest request) {
    Game game = new Game();

    return this.storeOrUpdate(request, game);
  }

  public Game update(BigInteger id, GameRequest request, Set<String> included) {
    Game game = this.findById(id, included);

    return this.storeOrUpdate(request, game);
  }

  public Game show(BigInteger id, Set<String> included) {
    return this.findById(id, included);
  }

  public List<Game> index(Set<String> included) {
    return this.gameRepository.findAll();
  }

  public void destroy(BigInteger id) {
    Game game = this.findById(id, null);

    this.gameRepository.delete(game);
  }

  private Game findById(BigInteger id, Set<String> included) {
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

  private Game storeOrUpdate(GameRequest request, Game game) {
    game.setCover(this.getCover(request));
    game.setStudio(this.getStudio(request));

    return this.gameRepository.save(this.gameMapper.request(request, game));
  }
}
