package henrotaym.env.http.resources;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.HashMap;
import java.util.Map;

public class Resource {

  private final Map<String, Object> fields = new HashMap<>();

  public Resource set(String key, Object value) {
    fields.put(key, value);

    return this;
  }

  @JsonAnyGetter
  public Map<String, Object> getFields() {
    return fields;
  }
}
