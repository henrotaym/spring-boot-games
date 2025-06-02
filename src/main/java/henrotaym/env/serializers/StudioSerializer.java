package henrotaym.env.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.http.resources.StudioResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.mappers.StudioMapper;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StudioSerializer {
  private final HasIncludablesSerializer hasIncludablesSerializer;
  private final GameMapper gameMapper;
  private final StudioMapper studioMapper;

  public String serialize(Studio studio, Set<String> include) throws JsonProcessingException {
    return this.hasIncludablesSerializer.serialize(this.resource(studio, include), include);
  }

  private StudioResource resource(Studio studio, Set<String> include) {
    StudioResource studioResource = this.studioMapper.resource(studio);

    if (include == null) {
      return studioResource;
    }

    if (include.contains("games")) {
      List<GameResource> gameResources =
          studio.getGames().stream().map(game -> this.gameMapper.resource(game)).toList();
      studioResource.setGames(gameResources);
    }

    return studioResource;
  }
}
