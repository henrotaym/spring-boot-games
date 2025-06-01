package henrotaym.env.factories.dto.events;

import henrotaym.env.dto.events.EventListenersMapping;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.listeners.Listener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile({ProfileName.QUEUE, ProfileName.TEST})
public class EventListenersMappingFactory {

  public EventListenersMapping create(Listener<?>... listeners) {
    final Map<Class<?>, List<Listener<?>>> mapping = new HashMap<>();
    for (Listener<?> listener : listeners) {
      Class<?> type = this.getListenerType(listener);
      mapping.putIfAbsent(type, new ArrayList<>());
      mapping.get(type).add(listener);
    }

    return new EventListenersMapping(mapping);
  }

  @SuppressWarnings("unchecked")
  private <T> Class<T> getListenerType(Listener<T> listener) {
    Class<T> type = null;
    for (Method method : listener.getClass().getMethods()) {
      if (method.getName() == "handle") {
        type = (Class<T>) method.getParameterTypes()[0];
        break;
      }
    }

    return type;
  }
}
