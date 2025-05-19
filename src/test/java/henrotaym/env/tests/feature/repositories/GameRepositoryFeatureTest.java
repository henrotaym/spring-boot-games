package henrotaym.env.tests.feature.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.CoverFactory;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameRepositoryFeatureTest extends ApplicationTest {
  @Autowired private GameFactory gameFactory;
  @Autowired private CoverFactory coverFactory;
  @Autowired private GameRepository gameRepository;
  @Autowired private CoverRepository coverRepository;

  @Test
  void it_associates_an_existing_cover_to_a_game() {
    Cover cover = this.coverFactory.create();
    Game game = this.gameFactory.create();

    game.setCover(cover);
    this.gameRepository.save(game);

    assertNotNull(game.getId());
    assertNotNull(cover.getUrl());
    assertNotNull(cover.getId());
    assertEquals(cover.getId(), game.getCover().getId());
    assertEquals(1, this.gameRepository.count());
    assertEquals(1, this.coverRepository.count());
  }
}
