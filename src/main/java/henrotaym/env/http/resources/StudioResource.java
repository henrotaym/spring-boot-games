package henrotaym.env.http.resources;

import com.fasterxml.jackson.annotation.JsonFilter;
import henrotaym.env.entities.HasConditionalFields;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonFilter("conditionalFields")
public class StudioResource implements HasConditionalFields {
  private final BigInteger id;
  private final String name;
  private List<GameResource> games;

  @Override
  public Set<String> getConditionalFields() {
    return Set.of("games");
  }
}
