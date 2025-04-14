package henrotaym.env.http.controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.services.GameService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("games")
public class GameController {
    private GameService gameService;
    
    @PostMapping("")
    public ResponseEntity<GameResource> store(@RequestBody @Valid GameRequest request) {
        GameResource game = this.gameService.store(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(game);
    }

    @GetMapping("{id}")
    public ResponseEntity<GameResource> show(@PathVariable BigInteger id) {
        GameResource game = this.gameService.show(id);

        return ResponseEntity.ok(game);
    }

    @PutMapping("{id}")
    public ResponseEntity<GameResource> update(@PathVariable BigInteger id, @RequestBody @Valid GameRequest request) {
        GameResource game = this.gameService.update(id, request);

        return ResponseEntity.ok(game);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable BigInteger id) {
        this.gameService.destroy(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<GameResource>> index() {
        List<GameResource> games = this.gameService.index();

        return ResponseEntity.ok(games);
    }
}
