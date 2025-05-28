package henrotaym.env.tests.feature.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.database.factories.TagFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Tag;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.repositories.TagRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameRepositoryFeatureTest extends ApplicationTest {
  @Autowired GameFactory gameFactory;
  @Autowired TagFactory tagFactory;
  @Autowired TagRepository tagRepository;
  @Autowired GameRepository gameRepository;
  @Autowired EntityManager entityManager;

  @Test
  public void it_associates_a_tag_to_a_game() {
    Game game = this.gameFactory.create();
    Tag tag = this.tagFactory.create();

    game.setTags(List.of(tag));
    this.gameRepository.save(game);

    entityManager.flush();
    entityManager.clear();

    Tag actualTag = this.tagRepository.findById(tag.getId()).get();

    assertFalse(actualTag.getGames().isEmpty());
  }
}
