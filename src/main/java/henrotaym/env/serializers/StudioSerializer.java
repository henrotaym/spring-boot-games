package henrotaym.env.serializers;

import henrotaym.env.entities.Studio;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.http.resources.StudioResource;
import henrotaym.env.mappers.CoverMapper;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.mappers.StudioMapper;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class StudioSerializer extends EntitySerializer<Studio, StudioResource> {
  private final GameMapper gameMapper;
  private final StudioMapper studioMapper;

  public StudioSerializer(
      HasIncludablesSerializer serializer,
      CoverMapper coverMapper,
      GameMapper gameMapper,
      StudioMapper studioMapper) {
    super(serializer);
    this.gameMapper = gameMapper;
    this.studioMapper = studioMapper;
  }

  @Override
  protected StudioResource attributes(Studio studio, Set<String> included) {
    return this.studioMapper.resource(studio);
  }

  @Override
  protected void relationships(Studio studio, Set<String> included, StudioResource resource) {
    if (included.contains("games")) {
      List<GameResource> games =
          studio.getGames().stream().map(g -> this.gameMapper.resource(g)).toList();
      resource.setGames(games);
    }
  }
}
