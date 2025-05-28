package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private BigInteger id;

  private String name;

  @OneToOne()
  @JoinColumn(name = "cover_id", unique = true)
  @JsonBackReference
  private Cover cover;

  @ManyToOne(optional = false)
  @JoinColumn(name = "studio_id", nullable = false)
  @JsonBackReference
  private Studio studio;

  @ManyToMany()
  @JoinTable(
      name = "game_tag",
      joinColumns = @JoinColumn(name = "game_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
  @JsonBackReference
  private List<Tag> tags = new ArrayList<Tag>();

  public void setTags(List<Tag> tags) {
    this.getTags().clear();
    this.getTags().addAll(tags);
  }
}
