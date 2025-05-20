package henrotaym.env.mappers;

import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.http.resources.StudioResource;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StudioMapper {
  private GameMapper gameMapper;

  public StudioResource resource(Studio studio) {
    List<GameResource> games =
        studio.getGames().stream().map(g -> this.gameMapper.resource(g)).toList();

    return new StudioResource(studio.getId(), studio.getName(), games);
  }

  public Studio request(StudioRequest request, Studio studio) {
    studio.setName(request.name());

    return studio;
  }
}
