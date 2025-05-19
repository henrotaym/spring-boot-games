package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.GameRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CoverRequest(@NotBlank String url, @Valid GameRelationshipRequest game) {}
