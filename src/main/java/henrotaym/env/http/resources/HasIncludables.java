package henrotaym.env.http.resources;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.Set;

@JsonFilter("included")
public interface HasIncludables {
  public Set<String> getIncludables();
}
