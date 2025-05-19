package henrotaym.env.mappers;

import henrotaym.env.entities.Cover;
import henrotaym.env.http.requests.CoverRequest;
import org.springframework.stereotype.Component;

@Component
public class CoverMapper {
  public Cover request(CoverRequest request, Cover cover) {
    cover.setUrl(request.url());

    return cover;
  }
}
