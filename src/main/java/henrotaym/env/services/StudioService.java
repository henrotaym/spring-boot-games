package henrotaym.env.services;

import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.resources.StudioResource;
import henrotaym.env.mappers.ResourceMapper;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.StudioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudioService {
  private GameRepository gameRepository;
  private StudioRepository studioRepository;
  private ResourceMapper resourceMapper;

  public StudioResource store(StudioRequest request) {
    Studio studio = new Studio();

    return this.storeOrUpdate(request, studio);
  }

  private StudioResource storeOrUpdate(StudioRequest request, Studio studio) {
    studio =
        this.studioRepository.save(this.resourceMapper.getStudioMapper().request(request, studio));

    return this.resourceMapper.studioResource(studio);
  }
}
