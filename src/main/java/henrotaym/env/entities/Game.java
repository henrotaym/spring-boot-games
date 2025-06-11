package henrotaym.env.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import java.util.Set;
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
  @JsonManagedReference
  private Cover cover;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "studio_id", nullable = false)
  @JsonManagedReference
  private Studio studio;

  @ManyToMany()
  @JoinTable(
      name = "game_tag",
      joinColumns = @JoinColumn(name = "game_id", nullable = false),
      inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false))
  @JsonManagedReference
  private List<Tag> tags = new ArrayList<Tag>();

  public void setTags(List<Tag> tags) {
    this.tags.clear();
    this.tags.addAll(tags);
  }

  public Set<String> getIncludables() {
    return Set.of("studio", "cover", "tags");
  }
}
