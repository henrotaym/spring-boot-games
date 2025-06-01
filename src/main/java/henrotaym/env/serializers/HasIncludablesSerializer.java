package henrotaym.env.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class HasIncludablesSerializer {
  private final ObjectMapper objectMapper;

  public <T extends HasIncludables> String serialize(T element, Set<String> include)
      throws JsonProcessingException {
    Set<String> includables = element.includables();
    Set<String> excluded =
        includables.stream()
            .filter(includable -> !include.contains(includable))
            .collect(Collectors.toSet());
    SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    filterProvider.addFilter("include", SimpleBeanPropertyFilter.serializeAllExcept(excluded));

    return this.objectMapper.writer(filterProvider).writeValueAsString(element);
  }
}
