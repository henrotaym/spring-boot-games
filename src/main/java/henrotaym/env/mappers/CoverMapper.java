package henrotaym.env.mappers;

import henrotaym.env.entities.Cover;
import henrotaym.env.http.resources.CoverResource;
import org.springframework.stereotype.Component;

@Component
public class CoverMapper {
  public CoverResource resource(Cover cover) {
    return new CoverResource(cover.getId(), cover.getUrl());
  }
}
