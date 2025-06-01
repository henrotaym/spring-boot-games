package henrotaym.env.tests.feature.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.debug.Debug;
import henrotaym.env.entities.Game;
import henrotaym.env.http.resources.GameResource;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.serializers.HasIncludablesSerializer;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GameSerializerFeatureTest extends ApplicationTest {
  @Autowired GameFactory gameFactory;
  @Autowired HasIncludablesSerializer hasIncluablesSerializer;
  @Autowired GameMapper gameMapper;

  @Test
  public void it_serializes_a_game() throws JsonProcessingException {
    Game game = this.gameFactory.create();
    Set<String> include = Set.of("studio", "tags");
    GameResource resource = this.gameMapper.resource(game);
    Debug.logger().dump(this.hasIncluablesSerializer.serialize(resource, include));
    // Debug.logger().dump();
  }
}
