package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StudioResource {
  private final BigInteger id;
  private final String name;
  private List<GameResource> games;
}
