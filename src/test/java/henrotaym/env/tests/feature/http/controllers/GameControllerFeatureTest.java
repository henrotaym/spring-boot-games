package henrotaym.env.tests.feature.http.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.CoverFactory;
import henrotaym.env.database.factories.GameFactory;
import henrotaym.env.database.factories.StudioFactory;
import henrotaym.env.database.factories.TagFactory;
import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.entities.Studio;
import henrotaym.env.entities.Tag;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.requests.relationships.CoverRelationshipRequest;
import henrotaym.env.http.requests.relationships.StudioRelationshipRequest;
import henrotaym.env.http.requests.relationships.TagRelationshipRequest;
import henrotaym.env.mappers.TagMapper;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import henrotaym.env.utils.api.JsonClient;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public class GameControllerFeatureTest extends ApplicationTest {
  @Autowired MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @Autowired TagMapper tagMapper;

  @Autowired GameRepository gameRepository;

  @Autowired CoverRepository coverRepository;

  @Autowired GameFactory gameFactory;

  @Autowired CoverFactory coverFactory;

  @Autowired StudioFactory studioFactory;

  @Autowired TagFactory tagFactory;

  @Autowired JsonClient jsonClient;

  @Test
  public void it_responds_to_store_url_without_cover() throws Exception {
    Studio studio = this.studioFactory.create();
    String name = "test";
    StudioRelationshipRequest studioRelationshipRequest =
        new StudioRelationshipRequest(studio.getId());
    GameRequest gameRequest =
        new GameRequest(
            name, null, studioRelationshipRequest, new ArrayList<TagRelationshipRequest>());

    this.jsonClient
        .request(request -> request.post("/games").content(gameRequest))
        .perform()
        .content("$.name", content -> content.value(name))
        .status(status -> status.isCreated());

    assertEquals(1, this.gameRepository.count());
  }

  @Test
  public void it_responds_to_store_url_with_an_existing_cover() throws Exception {
    Cover cover = this.coverFactory.create();
    Studio studio = this.studioFactory.create();
    Tag tag = this.tagFactory.create();
    String name = "test";
    CoverRelationshipRequest coverRelationshipRequest = new CoverRelationshipRequest(cover.getId());
    StudioRelationshipRequest studioRelationshipRequest =
        new StudioRelationshipRequest(studio.getId());
    List<TagRelationshipRequest> tagRelationshipRequests = new ArrayList<>();
    tagRelationshipRequests.add(this.tagMapper.relationshipRequest(tag));
    GameRequest gameRequest =
        new GameRequest(
            name, coverRelationshipRequest, studioRelationshipRequest, tagRelationshipRequests);

    this.jsonClient
        .request(request -> request.post("/games").content(gameRequest))
        .perform()
        .content("$.name", content -> content.value(name))
        .content("$.cover.id", content -> content.value(cover.getId()))
        .content("$.cover.url", content -> content.value(cover.getUrl()))
        .content("$.studio.id", content -> content.value(studio.getId()))
        .content("$.studio.name", content -> content.value(studio.getName()))
        .inList("$.tags", "id", tag.getId())
        .listSize("$.tags", 1)
        .status(status -> status.isCreated());

    assertEquals(1, this.coverRepository.count());
    assertEquals(1, this.gameRepository.count());
  }

  @Test
  public void it_throws_validation_message_to_store_url_with_wrongly_formated_cover()
      throws Exception {
    String name = "test";
    Studio studio = this.studioFactory.create();

    StudioRelationshipRequest studioRelationshipRequest =
        new StudioRelationshipRequest(studio.getId());

    CoverRelationshipRequest coverRelationshipRequest = new CoverRelationshipRequest(null);
    GameRequest gameRequest =
        new GameRequest(
            name,
            coverRelationshipRequest,
            studioRelationshipRequest,
            new ArrayList<TagRelationshipRequest>());

    this.jsonClient
        .request(request -> request.post("/games").content(gameRequest))
        .perform()
        .content("$.data.['cover.id']", content -> content.exists())
        .status(status -> status.isBadRequest());

    assertEquals(0, this.gameRepository.count());
  }

  @Test
  public void it_throws_validation_message_to_store_url_with_not_found_cover() throws Exception {
    String name = "test";
    CoverRelationshipRequest coverRelationshipRequest =
        new CoverRelationshipRequest(BigInteger.TEN);
    Studio studio = this.studioFactory.create();
    StudioRelationshipRequest studioRelationshipRequest =
        new StudioRelationshipRequest(studio.getId());

    GameRequest gameRequest =
        new GameRequest(
            name,
            coverRelationshipRequest,
            studioRelationshipRequest,
            new ArrayList<TagRelationshipRequest>());

    this.jsonClient
        .request(request -> request.post("/games").content(gameRequest))
        .perform()
        .content("$.data.['cover.id']", content -> content.exists())
        .status(status -> status.isBadRequest());

    assertEquals(0, this.gameRepository.count());
  }

  @Test
  public void it_responds_to_update_url_without_cover() throws Exception {
    String name = "test";
    Game game = this.gameFactory.create();
    StudioRelationshipRequest studioRelationshipRequest =
        new StudioRelationshipRequest(game.getStudio().getId());

    GameRequest gameRequest =
        new GameRequest(
            name, null, studioRelationshipRequest, new ArrayList<TagRelationshipRequest>());

    this.jsonClient
        .request(request -> request.put("/games/{id}", game.getId()).content(gameRequest))
        .perform()
        .content("$.name", content -> content.value(name))
        .status(status -> status.isOk());

    Game updatedGame = this.gameRepository.findById(game.getId()).get();
    assertEquals(name, updatedGame.getName());
    assertEquals(1, this.gameRepository.count());
  }

  @Test
  public void it_responds_to_update_url_with_an_existing_cover() throws Exception {
    Cover cover = this.coverFactory.create();
    Studio studio = this.studioFactory.create();
    Game game = this.gameFactory.create();
    String name = "test";
    StudioRelationshipRequest studioRelationshipRequest =
        new StudioRelationshipRequest(studio.getId());
    CoverRelationshipRequest coverRelationshipRequest = new CoverRelationshipRequest(cover.getId());
    GameRequest gameRequest =
        new GameRequest(
            name,
            coverRelationshipRequest,
            studioRelationshipRequest,
            new ArrayList<TagRelationshipRequest>());

    this.jsonClient
        .request(request -> request.put("/games/{id}", game.getId()).content(gameRequest))
        .perform()
        .content("$.name", content -> content.value(name))
        .content("$.cover.id", content -> content.value(cover.getId()))
        .content("$.cover.url", content -> content.value(cover.getUrl()))
        .status(status -> status.isOk());

    assertEquals(1, this.coverRepository.count());
    assertEquals(1, this.gameRepository.count());
  }
}
