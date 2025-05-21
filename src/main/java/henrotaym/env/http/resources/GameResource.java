package henrotaym.env.http.resources;

import java.math.BigInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class GameResource {
  private final BigInteger id;
  private final String name;
  private CoverResource cover;
  private StudioResource studio;
}
