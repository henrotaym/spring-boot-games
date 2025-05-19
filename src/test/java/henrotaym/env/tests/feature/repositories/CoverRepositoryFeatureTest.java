package henrotaym.env.tests.feature.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.CoverFactory;
import henrotaym.env.entities.Cover;
import henrotaym.env.repositories.CoverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CoverRepositoryFeatureTest extends ApplicationTest {
  @Autowired private CoverRepository coverRepository;
  @Autowired private CoverFactory coverFactory;

  @Test
  void it_creates_a_cover_and_saves_to_db() {
    String url = ":url";
    Cover cover = this.coverFactory.create(c -> c.setUrl(url));

    assertNotNull(cover.getId());
    assertEquals(url, cover.getUrl());
    assertEquals(1, this.coverRepository.count());
  }
}
