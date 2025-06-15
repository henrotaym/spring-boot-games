package henrotaym.env.tests.feature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.querydsl.jpa.impl.JPAQueryFactory;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.database.factories.StudioFactory;
import henrotaym.env.database.factories.TagFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.QGame;
import henrotaym.env.entities.QStudio;
import henrotaym.env.entities.QTag;
import henrotaym.env.entities.Studio;
import henrotaym.env.entities.Tag;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryDslServiceFeatureTest extends ApplicationTest {
  @Autowired EntityManager entityManager;
  @Autowired GameFactory gameFactory;
  @Autowired StudioFactory studioFactory;
  @Autowired TagFactory tagFactory;

  @Test
  public void it_queries_element_using_query_dsl() {
    Tag tag = this.tagFactory.create(t -> t.setName("FPS"));
    Studio studio = this.studioFactory.create(s -> s.setName("epic games"));
    Game game =
        this.gameFactory.create(
            g -> {
              g.setName("paragon");
              g.setStudio(studio);
              g.setTags(List.of(tag));
            });
    this.gameFactory.create();

    JPAQueryFactory query = new JPAQueryFactory(entityManager);
    List<Game> games =
        query
            .selectFrom(QGame.game)
            .leftJoin(QGame.game.studio, QStudio.studio)
            .fetchJoin()
            .leftJoin(QGame.game.tags, QTag.tag)
            .fetchJoin()
            .where(QGame.game.name.contains("parag"))
            .where(QGame.game.studio.name.contains("epic"))
            .where(QGame.game.tags.isNotEmpty())
            .fetch();

    assertEquals(1, games.size());
    assertEquals(game.getId(), games.getFirst().getId());
  }
}
