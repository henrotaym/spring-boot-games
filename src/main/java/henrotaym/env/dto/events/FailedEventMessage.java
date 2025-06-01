package henrotaym.env.dto.events;

import henrotaym.env.listeners.Listener;
import java.util.List;
import lombok.Getter;

@Getter
public class FailedEventMessage<T> extends EventMessage<T> {

  private final List<Class<Listener<T>>> failedListeners;

  public FailedEventMessage(Class<T> type, T payload, List<Class<Listener<T>>> failedListeners) {
    super(type, payload);
    this.failedListeners = failedListeners;
  }
}
