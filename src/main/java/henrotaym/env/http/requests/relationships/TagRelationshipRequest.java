package henrotaym.env.http.requests.relationships;

import henrotaym.env.annotations.ExistsInDatabase;
import henrotaym.env.repositories.TagRepository;
import java.math.BigInteger;

public record TagRelationshipRequest(
    @ExistsInDatabase(repository = TagRepository.class) BigInteger id) {}
