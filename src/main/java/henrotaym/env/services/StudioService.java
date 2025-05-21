package henrotaym.env.services;

import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.resources.ConditionalSerializer;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.mappers.StudioMapper;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.StudioRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudioService {
  private GameRepository gameRepository;
  private StudioRepository studioRepository;
  private StudioMapper studioMapper;
  private ResourceMapper resourceMapper;

  public ConditionalSerializer store(StudioRequest request) {
    Studio studio = new Studio();

    return this.storeOrUpdate(request, studio);
  }

  private ConditionalSerializer storeOrUpdate(StudioRequest request, Studio studio) {
    List<Game> games =
        request.games().stream().map(g -> this.gameRepository.findById(g.id()).get()).toList();

    studio.setGames(games);
    studio = this.studioRepository.save(this.studioMapper.request(request, studio));

    return this.resourceMapper.studio(studio);
  }
}
