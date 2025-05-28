package henrotaym.env.http.requests;

import jakarta.validation.constraints.NotBlank;

public record StudioRequest(@NotBlank String name) {}
