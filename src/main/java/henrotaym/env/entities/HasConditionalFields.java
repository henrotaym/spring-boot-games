package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

@JsonFilter("conditionalFields")
public interface HasConditionalFields {
  @JsonIgnore
  public Set<String> getConditionalFields();
}
