package henrotaym.env.serializers;

import java.util.Set;

// TODO Reactive filter when done
// @JsonFilter(value = "include")
public interface HasIncludables {
  public Set<String> includables();
}
