package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tags")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private String name;

  @ManyToMany(mappedBy = "tags")
  @JsonManagedReference
  private List<Game> games = new ArrayList<>();

  public Tag setGames(List<Game> games) {
    this.getGames().forEach(game -> game.getTags().remove(this));
    games.forEach(game -> game.getTags().add(this));
    this.getGames().clear();
    this.getGames().addAll(games);

    return this;
  }
}
