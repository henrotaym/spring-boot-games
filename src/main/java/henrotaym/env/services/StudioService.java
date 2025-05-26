package henrotaym.env.services;

import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.mappers.StudioMapper;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.StudioRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudioService {

  private final StudioMapper studioMapper;
  private final GameRepository gameRepository;
  private final StudioRepository studioRepository;

  public Studio store(StudioRequest request, Set<String> included) {
    Studio studio = new Studio();

    return this.storeOrUpdate(request, studio, included);
  }

  private Studio storeOrUpdate(StudioRequest request, Studio studio, Set<String> included) {
    List<Game> games =
        request.games().stream().map(g -> this.gameRepository.findById(g.id()).get()).toList();
    studio.setGames(games);

    return this.studioRepository.save(this.studioMapper.request(request, studio));
  }
}
