package henrotaym.env.serializers;

import henrotaym.env.entities.Game;
import henrotaym.env.http.resources.CoverResource;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.http.resources.StudioResource;
import henrotaym.env.mappers.CoverMapper;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.mappers.StudioMapper;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class GameSerializer extends EntitySerializer<Game, GameResource> {
  private final CoverMapper coverMapper;
  private final GameMapper gameMapper;
  private final StudioMapper studioMapper;

  public GameSerializer(
      HasIncludablesSerializer serializer,
      CoverMapper coverMapper,
      GameMapper gameMapper,
      StudioMapper studioMapper) {
    super(serializer);
    this.coverMapper = coverMapper;
    this.gameMapper = gameMapper;
    this.studioMapper = studioMapper;
  }

  @Override
  protected GameResource attributes(Game game, Set<String> included) {
    return this.gameMapper.resource(game);
  }

  @Override
  protected void relationships(Game game, Set<String> included, GameResource resource) {
    if (included.contains("studio")) {
      StudioResource studioResource = this.studioMapper.resource(game.getStudio());
      resource.setStudio(studioResource);
    }

    if (included.contains("cover") && game.getCover() != null) {
      CoverResource coverResource = this.coverMapper.resource(game.getCover());
      resource.setCover(coverResource);
    }
  }
}
