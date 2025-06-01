package henrotaym.env.dto.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EventMessage<T> {
  private final Class<T> type;
  private final T payload;
}
