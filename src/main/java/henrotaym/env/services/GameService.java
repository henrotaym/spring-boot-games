package henrotaym.env.services;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.repositories.GameRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GameService {
    private GameRepository gameRepository;
    private GameMapper gameMapper;

    public GameResource store(GameRequest request) {
        Game game = new Game();
        game = this.gameRepository.save(
            this.gameMapper.request(request, game)
        );

        return this.gameMapper.resource(game);
    }

    public GameResource update(BigInteger id, GameRequest request) {
        Game game = this.gameRepository.findById(id)
            .orElseThrow();

        game = this.gameRepository.save(
            this.gameMapper.request(request, game)
        );

        return this.gameMapper.resource(game);
    }
}
