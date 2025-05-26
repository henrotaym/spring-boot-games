package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class GameResource implements HasIncludables {
  private final BigInteger id;
  private final String name;
  private CoverResource cover;
  private StudioResource studio;

  @Override
  public Set<String> getIncludables() {
    return Set.of("cover", "studio");
  }
}
