package henrotaym.env.http.requests.relationships;

import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;

public record CoverRelationshipRequest(@NotNull BigInteger id) {}
