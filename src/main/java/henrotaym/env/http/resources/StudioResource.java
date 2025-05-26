package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StudioResource implements HasIncludables {
  private final BigInteger id;
  private final String name;
  private List<GameResource> games;

  @Override
  public Set<String> getIncludables() {
    return Set.of("games");
  }
}
