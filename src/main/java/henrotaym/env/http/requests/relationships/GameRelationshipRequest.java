package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.GameRepository;
import java.math.BigInteger;

public record GameRelationshipRequest(
    @ExistsInDatabase(repository = GameRepository.class) BigInteger id) {}
