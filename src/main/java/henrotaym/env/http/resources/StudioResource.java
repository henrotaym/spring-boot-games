package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.util.List;

public record StudioResource(BigInteger id, String name, List<GameResource> games) {}
