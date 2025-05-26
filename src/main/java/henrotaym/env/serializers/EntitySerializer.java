package henrotaym.env.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.http.resources.HasIncludables;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class EntitySerializer<E, R extends HasIncludables> {
  protected final HasIncludablesSerializer serializer;

  public String serialize(E entity, Set<String> included) throws JsonProcessingException {
    R resource = this.attributes(entity, included);
    Boolean hasIncluded = included != null && !included.isEmpty();

    if (hasIncluded) {
      this.relationships(entity, included, resource);
    }

    return this.serializer.serialize(resource, included);
  }

  public List<String> serializeList(List<E> entities, Set<String> included)
      throws JsonProcessingException {
    List<String> list = new ArrayList<>(entities.size());
    for (E entity : entities) {
      list.add(this.serialize(entity, included));
    }

    return list;
  }

  protected abstract R attributes(E entity, Set<String> included);

  protected abstract void relationships(E entity, Set<String> included, R resource);
}
