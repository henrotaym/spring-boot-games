package henrotaym.env.feature;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import henrotaym.env.ApplicationTest;
import henrotaym.env.entities.Game;
import henrotaym.env.repositories.GameRepository;

public class ContextLoadsTest extends ApplicationTest {
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void it_starts_application_and_run_migrations() {
        Game game = new Game(null, "minecraf");
        this.gameRepository.save(game);
    }
}
