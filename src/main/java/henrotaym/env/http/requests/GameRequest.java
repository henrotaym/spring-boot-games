package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record GameRequest(@NotBlank String name, @Valid CoverRelationshipRequest cover) {}
