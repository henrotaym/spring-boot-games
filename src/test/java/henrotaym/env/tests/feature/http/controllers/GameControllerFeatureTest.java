package henrotaym.env.tests.feature.http.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.utils.api.JsonClient;

public class GameControllerFeatureTest extends ApplicationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameFactory gameFactory;

    @Autowired
    JsonClient jsonClient;

    @Test
    public void it_responds_to_store_url() throws Exception {
        String name = "test";
        Map<String, String> body = Map.of("name", name);

        this.jsonClient.request(request -> request
            .post("/games")
            .content(body)
        )
        .perform()
        .contains("$.name", content -> content.value(name))
        .status(status -> status.isCreated());

        assertEquals(1, this.gameRepository.count());
    }

    @Test
    public void it_responds_to_update_url() throws Exception
    {
        Game game = this.gameFactory.create();
        String newName = "roblox"; 
        Map<String, String> body = Map.of("name", newName);

        this.jsonClient.request(request -> request
            .put("/games/{id}", game.getId())
            .content(body)
        )
        .perform()
        .contains("$.name", content -> content.value(newName))
        .status(status -> status.isOk());

        Game updatedGame = this.gameRepository.findById(game.getId()).get();
        assertEquals(newName, updatedGame.getName());
        assertEquals(1, this.gameRepository.count());
    }
}
