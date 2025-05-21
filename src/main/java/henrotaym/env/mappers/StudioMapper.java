package henrotaym.env.mappers;

import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.resources.StudioResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StudioMapper {

  public StudioResource resource(Studio studio) {
    return new StudioResource(studio.getId(), studio.getName());
  }

  public Studio request(StudioRequest request, Studio studio) {
    studio.setName(request.name());

    return studio;
  }
}
