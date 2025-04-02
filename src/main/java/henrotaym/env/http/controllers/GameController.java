package henrotaym.env.http.controllers;

import java.math.BigInteger;
import java.util.List;

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
    public GameResource store(@RequestBody @Valid GameRequest request) {
        return this.gameService.store(request);
    }

    @GetMapping("{id}")
    public Game show(@PathVariable BigInteger id) {
        return this.repository.findById(id)
            .orElseThrow();
    }

    @PutMapping("{id}")
    public GameResource update(@PathVariable BigInteger id, @RequestBody @Valid GameRequest request) {
        return this.gameService.update(id, request);
    }

    @DeleteMapping("{id}")
    public void destroy(@PathVariable BigInteger id) {
        Game game = this.repository.findById(id)
            .orElseThrow();

        this.repository.delete(game);
    }

    @GetMapping("")
    public List<Game> index() {
        return this.repository.findAll();
    }
}
