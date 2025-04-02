package henrotaym.env.http.controllers;

import java.math.BigInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import henrotaym.env.entities.Game;
import henrotaym.env.repositories.GameRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("games")
public class GameController {
    private GameRepository repository;
    
    @PostMapping("")
    public Game store(@RequestBody Game request) {
        return this.repository.save(request);
    }

    @GetMapping("{id}")
    public Game show(@PathVariable BigInteger id) {
        return this.repository.findById(id)
            .orElseThrow();
    }

    @PutMapping("{id}")
    public Game update(@PathVariable BigInteger id, @RequestBody Game request) {
        Game game = this.repository.findById(id)
            .orElseThrow();

        game.setName(request.getName());

        return this.repository.save(game);
    }
}
