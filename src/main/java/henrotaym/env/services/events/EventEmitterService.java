package henrotaym.env.services.events;

import henrotaym.env.dto.events.EventMessage;
import henrotaym.env.dto.events.FailedEventMessage;
import henrotaym.env.enums.events.QueueName;
import henrotaym.env.listeners.Listener;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventEmitterService {
  private final RabbitTemplate rabbitTemplate;

  public <T> void dispatch(T event) {
    EventMessage<T> message = new EventMessage<>(this.getEventType(event), event);

    this.dispatchToQueue(message);
  }

  @SuppressWarnings("unchecked")
  public <T> void failed(T event, List<Listener<T>> listeners) {
    List<Class<Listener<T>>> listenerClasses =
        listeners.stream().map(l -> (Class<Listener<T>>) l.getClass()).toList();
    FailedEventMessage<T> message =
        new FailedEventMessage<>(this.getEventType(event), event, listenerClasses);

    this.dispatchToQueue(message);
  }

  @SuppressWarnings("unchecked")
  private <T> Class<T> getEventType(T event) {
    return (Class<T>) event.getClass();
  }

  private <T> void dispatchToQueue(EventMessage<T> message) {
    String queueName =
        message instanceof FailedEventMessage ? QueueName.DEFAULT_DLQ : QueueName.DEFAULT;

    this.rabbitTemplate.convertAndSend(queueName, message);
  }
}
