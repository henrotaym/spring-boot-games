package henrotaym.env.tests.feature.serializers;

import com.querydsl.jpa.impl.JPAQueryFactory;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.QGame;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestastosTest extends ApplicationTest {

  @Autowired EntityManager entityManager;
  @Autowired GameFactory gameFactory;

  @Test
  public void it_retrieves_a_game() {
    Game knownGame = gameFactory.create();
    QGame qGame = QGame.game;
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    Game game =
        queryFactory
            .selectFrom(qGame)
            .where(qGame.name.contains(knownGame.getName() + "nope"))
            .fetchFirst();

    System.out.println(game);
  }
}
