package henrotaym.env.http.controllers;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.StudioRequest;
import henrotaym.env.http.resources.StudioResource;
import henrotaym.env.services.StudioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("studios")
@Profile(ProfileName.HTTP)
public class StudioController {
  private StudioService studioService;

  @PostMapping("")
  public ResponseEntity<StudioResource> store(@RequestBody @Valid StudioRequest request) {
    StudioResource game = this.studioService.store(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(game);
  }
}
