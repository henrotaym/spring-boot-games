package henrotaym.env.http.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.entities.Studio;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.serializers.StudioSerializer;
import henrotaym.env.services.StudioService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("studios")
public class StudioController {
  private final StudioService studioService;
  private final StudioSerializer studioSerializer;

  @PostMapping("")
  public ResponseEntity<String> store(
      @RequestBody @Valid StudioRequest request,
      @RequestParam(required = false) Set<String> included)
      throws JsonProcessingException {
    Studio studio = this.studioService.store(request, included);
    String body = this.studioSerializer.serialize(studio, included);

    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }
}
