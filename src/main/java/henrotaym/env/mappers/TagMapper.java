package henrotaym.env.mappers;

import henrotaym.env.entities.Tag;
import henrotaym.env.http.requests.relationships.TagRelationshipRequest;
import henrotaym.env.http.resources.TagResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TagMapper {

  public TagResource resource(Tag tag) {
    return new TagResource(tag.getId(), tag.getName());
  }

  public TagRelationshipRequest relationshipRequest(Tag tag) {
    return new TagRelationshipRequest(tag.getId());
  }
}
