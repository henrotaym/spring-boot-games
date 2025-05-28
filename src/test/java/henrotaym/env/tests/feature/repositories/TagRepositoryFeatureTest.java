package henrotaym.env.tests.feature.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.database.factories.TagFactory;
import henrotaym.env.debug.Debug;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Tag;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.TagRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TagRepositoryFeatureTest extends ApplicationTest {
  @Autowired GameFactory gameFactory;
  @Autowired TagFactory tagFactory;
  @Autowired TagRepository tagRepository;
  @Autowired GameRepository gameRepository;
  @Autowired EntityManager entityManager;

  @Test
  public void it_associates_a_game_to_a_tag() throws Exception {
    Game game = this.gameFactory.create();
    Tag tag = this.tagFactory.create();

    tag.setGames(List.of(game));
    this.tagRepository.save(tag);

    entityManager.flush();
    entityManager.clear();

    Tag actualTag = this.tagRepository.findById(tag.getId()).get();
    Game actualGame = this.gameRepository.findById(game.getId()).get();

    assertEquals(1, actualTag.getGames().size());
    assertEquals(game.getId(), actualTag.getGames().getFirst().getId());
    assertEquals(1, actualGame.getTags().size());
    assertEquals(tag.getId(), actualGame.getTags().getFirst().getId());
  }

  @Test
  public void it_syncs_games_to_tag() throws JsonProcessingException {
    Game game1 = this.gameFactory.create();
    Tag tag = this.tagFactory.create(t -> t.setGames(List.of(game1)));
    Game game2 = this.gameFactory.create();

    tag.setGames(List.of(game2));
    this.tagRepository.save(tag);

    entityManager.flush();
    entityManager.clear();

    Tag actualTag = this.tagRepository.findById(tag.getId()).get();
    String pretty = Debug.logger().pretty(actualTag);
    Game actualGame1 = this.gameRepository.findById(game1.getId()).get();
    Game actualGame2 = this.gameRepository.findById(game2.getId()).get();

    assertEquals(1, actualTag.getGames().size());
    assertEquals(game2.getId(), actualTag.getGames().getFirst().getId());
    assertEquals(1, actualGame2.getTags().size());
    assertEquals(tag.getId(), actualGame2.getTags().getFirst().getId());
    assertEquals(0, actualGame1.getTags().size());
  }
}
