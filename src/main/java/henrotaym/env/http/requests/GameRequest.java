package henrotaym.env.http.requests;

import jakarta.validation.constraints.NotBlank;

public record GameRequest(@NotBlank String name) {}
