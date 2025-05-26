package henrotaym.env.http.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.serializers.GameSerializer;
import henrotaym.env.services.GameService;
import jakarta.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("games")
public class GameController {
  private GameService gameService;
  private final GameSerializer gameSerializer;

  @PostMapping("")
  public ResponseEntity<String> store(
      @RequestBody @Valid GameRequest request, @RequestParam(required = false) Set<String> included)
      throws JsonProcessingException {
    Game game = this.gameService.store(request);
    String body = this.gameSerializer.serialize(game, included);

    return ResponseEntity.status(HttpStatus.CREATED).body(body);
  }

  @GetMapping("{id}")
  public ResponseEntity<String> show(
      @PathVariable BigInteger id, @RequestParam(required = false) Set<String> included)
      throws JsonProcessingException {
    Game game = this.gameService.show(id, included);
    String body = this.gameSerializer.serialize(game, included);

    return ResponseEntity.ok(body);
  }

  @PutMapping("{id}")
  public ResponseEntity<String> update(
      @PathVariable BigInteger id,
      @RequestBody @Valid GameRequest request,
      @RequestParam(required = false) Set<String> included)
      throws JsonProcessingException {
    Game game = this.gameService.update(id, request, included);
    String body = this.gameSerializer.serialize(game, included);

    return ResponseEntity.ok(body);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
    this.gameService.destroy(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("")
  public ResponseEntity<List<String>> index(@RequestParam(required = false) Set<String> included)
      throws JsonProcessingException {
    List<Game> games = this.gameService.index(included);

    return ResponseEntity.ok(this.gameSerializer.serializeList(games, included));
  }
}
