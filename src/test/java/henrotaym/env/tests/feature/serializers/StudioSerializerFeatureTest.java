package henrotaym.env.tests.feature.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.StudioFactory;
import henrotaym.env.entities.Studio;
import henrotaym.env.mappers.GameMapper;
import henrotaym.env.serializers.StudioSerializer;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StudioSerializerFeatureTest extends ApplicationTest {
  @Autowired StudioFactory studioFactory;
  @Autowired StudioSerializer studioSerializer;
  @Autowired GameMapper gameMapper;

  @Test
  public void it_serializes_a_game() throws JsonProcessingException {
    Studio studio = this.studioFactory.create();
    Set<String> include = Set.of();
    String test = this.studioSerializer.serialize(studio, null);
    System.out.println();
  }
}
