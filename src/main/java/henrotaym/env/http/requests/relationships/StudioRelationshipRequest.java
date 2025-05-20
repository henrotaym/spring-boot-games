package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.StudioRepository;
import java.math.BigInteger;

public record StudioRelationshipRequest(
    @ExistsInDatabase(repository = StudioRepository.class) BigInteger id) {}
