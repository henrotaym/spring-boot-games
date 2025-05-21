package henrotaym.env.http.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import henrotaym.env.entities.HasConditionalFields;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConditionalSerializer {
  private final ObjectMapper mapper;
  private Set<String> excludedFields;
  private HasConditionalFields element;

  public ConditionalSerializer from(HasConditionalFields element, Set<String> includedFields) {
    this.setElement(element);
    this.setExcludedFields(element.getConditionalFields(), includedFields);

    return this;
  }

  public ConditionalSerializer ifIncluded(String field, Runnable callback) {
    if (!this.excludedFields.contains(field)) {
      callback.run();
    }

    return this;
  }

  public String serialize() throws JsonProcessingException {
    SimpleFilterProvider filterProvider = new SimpleFilterProvider();
    filterProvider.addFilter(
        "conditionalFields", SimpleBeanPropertyFilter.serializeAllExcept(this.excludedFields));

    return this.mapper.writer(filterProvider).writeValueAsString(this.element);
  }

  private void setExcludedFields(Set<String> conditionalFields, Set<String> includedFields) {
    this.excludedFields =
        conditionalFields.stream()
            .filter(field -> !includedFields.contains(field))
            .collect(Collectors.toSet());
  }

  private void setElement(HasConditionalFields element) {
    this.element = element;
  }
}
