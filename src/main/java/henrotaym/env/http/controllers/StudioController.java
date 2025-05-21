package henrotaym.env.http.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.resources.ConditionalSerializer;
import henrotaym.env.services.StudioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("studios")
public class StudioController {
  private StudioService studioService;

  @PostMapping("")
  public ResponseEntity<String> store(@RequestBody @Valid StudioRequest request)
      throws JsonProcessingException {
    ConditionalSerializer serializer = this.studioService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(serializer.serialize());
  }
}
