package henrotaym.env.database.factories;

import henrotaym.env.entities.Cover;
import henrotaym.env.entities.Game;
import henrotaym.env.repositories.CoverRepository;
import henrotaym.env.repositories.GameRepository;
import java.util.function.Consumer;
import lombok.AllArgsConstructor;
import net.datafaker.Faker;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CoverFactory {
  private Faker faker;
  private CoverRepository coverRepository;

  public Cover make(Consumer<Cover> callback) {
    Cover cover = new Cover();
    cover.setUrl(this.faker.internet().url());

    callback.accept(cover);

    return cover;
  }

  public Cover make() {
    return this.make((_) -> {});
  }

  public Cover create(Consumer<Cover> callback) {
    return this.coverRepository.save(this.make(callback));
  }

  public Cover create() {
    return this.create((_) -> {});
  }
}
