package henrotaym.env.services.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import henrotaym.env.dto.events.EventListenersMapping;
import henrotaym.env.dto.events.EventMessage;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.enums.events.QueueName;
import henrotaym.env.listeners.Listener;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile({ProfileName.QUEUE, ProfileName.TEST})
public class EventDispatcherService {
  private final ObjectMapper objectMapper;
  private final EventListenersMapping eventListenersMapping;
  private final EventEmitterService eventEmitterService;

  @RabbitListener(queues = QueueName.DEFAULT)
  public <T> void dispatch(EventMessage<T> message) {
    List<Listener<T>> failedListeners = new ArrayList<>();
    T payload = this.objectMapper.convertValue(message.getPayload(), message.getType());
    List<Listener<?>> listeners =
        this.eventListenersMapping.getMapping().getOrDefault(message.getType(), List.of());
    for (Listener<?> listener : listeners) {
      @SuppressWarnings("unchecked")
      Listener<T> typedListener = (Listener<T>) listener;
      try {
        typedListener.handle(payload);
      } catch (Exception exception) {
        log.error(exception.getLocalizedMessage(), exception);
        failedListeners.add(typedListener);
      }
    }

    if (!failedListeners.isEmpty()) {
      this.eventEmitterService.failed(payload, failedListeners);
    }
  }
}
