package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class GameRequest {
  @NotBlank private final String name;
  @Valid private final CoverRelationshipRequest cover;
}
