package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.GameRelationshipRequest;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record StudioRequest(@NotBlank String name, List<GameRelationshipRequest> games) {}
