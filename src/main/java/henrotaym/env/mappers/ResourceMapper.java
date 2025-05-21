package henrotaym.env.mappers;

import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.resources.ConditionalSerializer;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.http.resources.StudioResource;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ResourceMapper {
  private final CoverMapper coverMapper;
  private final GameMapper gameMapper;
  private final StudioMapper studioMapper;
  private final ConditionalSerializer serializer;

  public GameResource game(Game game) {
    GameResource resource = this.gameMapper.resource(game);
    resource.setStudio(this.studioMapper.resource(game.getStudio()));
    if (game.getCover() != null) {
      resource.setCover(this.coverMapper.resource(game.getCover()));
    }

    return resource;
  }

  public ConditionalSerializer studio(Studio studio) {
    StudioResource resource = this.studioMapper.resource(studio);
    this.serializer
        .from(resource, Set.of())
        .ifIncluded(
            "games",
            () ->
                resource.setGames(
                    studio.getGames().stream().map(this.gameMapper::resource).toList()));

    return this.serializer;
  }
}
