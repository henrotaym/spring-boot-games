package henrotaym.env.queues.listeners;

import henrotaym.env.queues.events.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("queue")
public class UseUpdatedListener implements Listener<UserUpdatedEvent> {
  @RetryableTopic(attempts = "1")
  @KafkaListener(topics = UserUpdatedEvent.EVENT_NAME)
  @Override
  public void listen(UserUpdatedEvent event) {
    log.info("consumed " + event.getMessage());
  }
}
