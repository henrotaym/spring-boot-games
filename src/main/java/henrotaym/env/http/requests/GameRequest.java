package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import henrotaym.env.http.requests.relationships.StudioRelationshipRequest;
import henrotaym.env.http.requests.relationships.TagRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record GameRequest(
    @NotBlank String name,
    @Valid CoverRelationshipRequest cover,
    @Valid @NotNull StudioRelationshipRequest studio,
    @Valid @NotNull List<TagRelationshipRequest> tags) {}
