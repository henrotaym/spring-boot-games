package henrotaym.env.mappers;

import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.resources.CoverResource;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.http.resources.StudioResource;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class ResourceMapper {
  private final CoverMapper coverMapper;
  private final GameMapper gameMapper;
  private final StudioMapper studioMapper;

  public GameResource game(Game game, Set<String> included) {
    GameResource gameResource = this.gameMapper.resource(game);
    if (included == null) {
      return gameResource;
    }

    if (included.contains("studio")) {
      StudioResource studioResource = this.studioMapper.resource(game.getStudio());
      gameResource.setStudio(studioResource);
    }

    if (included.contains("cover") && game.getCover() != null) {
      CoverResource coverResource = this.coverMapper.resource(game.getCover());
      gameResource.setCover(coverResource);
    }

    return gameResource;
  }

  public StudioResource studioResource(Studio studio) {
    List<GameResource> games =
        studio.getGames().stream().map(g -> this.gameMapper.resource(g)).toList();
    StudioResource studioResource = this.studioMapper.resource(studio);
    studioResource.setGames(games);

    return studioResource;
  }

  public CoverResource coverResource(Cover cover) {
    return this.coverMapper.resource(cover);
  }
}
