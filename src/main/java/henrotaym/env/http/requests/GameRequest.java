package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import henrotaym.env.http.requests.relationships.StudioRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameRequest(
    @NotBlank String name,
    @Valid CoverRelationshipRequest cover,
    @Valid @NotNull StudioRelationshipRequest studio) {}
