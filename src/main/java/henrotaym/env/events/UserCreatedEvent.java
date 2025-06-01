package henrotaym.env.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserCreatedEvent {
  private final Integer bytes;
}
