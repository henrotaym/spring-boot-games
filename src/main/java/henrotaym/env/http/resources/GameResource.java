package henrotaym.env.http.resources;

import henrotaym.env.serializers.HasIncludables;
import java.math.BigInteger;
import java.util.List;
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
  private List<TagResource> tags;

  @Override
  public Set<String> includables() {
    return Set.of("studio", "cover", "tags");
  }
}
