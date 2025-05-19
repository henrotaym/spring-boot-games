package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.CoverRepository;
import java.math.BigInteger;

public record CoverRelationshipRequest(
    @ExistsInDatabase(repository = CoverRepository.class) BigInteger id) {}
