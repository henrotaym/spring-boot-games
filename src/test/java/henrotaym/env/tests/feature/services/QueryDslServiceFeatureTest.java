package henrotaym.env.tests.feature.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.database.factories.StudioFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.repositories.GameRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryDslServiceFeatureTest extends ApplicationTest {
  @Autowired GameFactory gameFactory;
  @Autowired StudioFactory studioFactory;
  @Autowired JPAQueryFactory jpaQueryFactory;
  @Autowired GameRepository gameRepository;

  @Test
  public void it_retrieves_data() {
    Game includedGame = this.gameFactory.create(g -> g.setName("yoloswarg"));
    // Flush pour s'assurer que les données sont persistées
    // entityManager.flush();
    // entityManager.clear();
    this.gameFactory.create();
    // List<Game> games =
    //     this.jpaQueryFactory
    //         .selectFrom(QGame.game)
    //         // .where(QGame.game.studio.name.eq(includedGame.getStudio().getName()))
    //         // .leftJoin(QGame.game.studio, QStudio.studio)
    //         // .fetchJoin()
    //         .fetch();
    String name = "name";
    List<Game> games = this.gameRepository.findAll();
    games.getFirst().getStudio().getName();
    // assertEquals(1, games.size());
    // assertEquals(includedGame.getId(), games.getFirst().getId());
  }
}
