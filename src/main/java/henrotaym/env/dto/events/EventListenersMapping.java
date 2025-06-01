package henrotaym.env.dto.events;

import henrotaym.env.listeners.Listener;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EventListenersMapping {
  private final Map<Class<?>, List<Listener<?>>> mapping;
}
