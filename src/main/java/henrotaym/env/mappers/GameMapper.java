package henrotaym.env.mappers;

import henrotaym.env.entities.Game;
import henrotaym.env.http.requests.GameRequest;
import henrotaym.env.http.requests.relationships.GameRelationshipRequest;
import henrotaym.env.http.resources.GameResource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GameMapper {

  public GameResource resource(Game game) {
    return new GameResource(game.getId(), game.getName());
  }

  public Game request(GameRequest request, Game game) {
    game.setName(request.name());

    return game;
  }

  public GameRelationshipRequest relationshipRequest(Game game) {
    return new GameRelationshipRequest(game.getId());
  }
}
