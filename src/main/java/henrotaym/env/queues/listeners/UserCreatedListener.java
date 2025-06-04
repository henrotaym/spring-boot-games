package henrotaym.env.queues.listeners;

import henrotaym.env.queues.events.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("queue")
public class UserCreatedListener implements Listener<UserCreatedEvent> {
  @Override
  @KafkaListener(topics = UserCreatedEvent.EVENT_NAME)
  public void listen(UserCreatedEvent event) {
    log.info("consumed " + event.getMessage());
  }
}
