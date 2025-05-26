package henrotaym.env.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import henrotaym.env.http.resources.HasIncludables;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HasIncludablesSerializer {
  private final ObjectMapper mapper;

  public <T extends HasIncludables> String serialize(T hasIncludables, Set<String> included)
      throws JsonProcessingException {
    SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    filterProvider.addFilter(
        "included",
        SimpleBeanPropertyFilter.serializeAllExcept(this.getExcluded(hasIncludables, included)));

    return this.mapper.writer(filterProvider).writeValueAsString(hasIncludables);
  }

  protected <T extends HasIncludables> Set<String> getExcluded(
      T hasIncludables, Set<String> included) {
    if (included == null) {
      return hasIncludables.getIncludables();
    }

    return hasIncludables.getIncludables().stream()
        .filter(field -> !included.contains(field))
        .collect(Collectors.toSet());
  }
}
