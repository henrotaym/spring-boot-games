package henrotaym.env.queues.listeners;

import henrotaym.env.annotations.KafkaRetryableListener;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.UserCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class UserCreatedListener implements Listener<UserCreatedEvent> {
  @Override
  @KafkaRetryableListener(UserCreatedEvent.EVENT_NAME)
  public void listen(UserCreatedEvent event) {
    log.info("consumed " + event.getMessage());
  }
}
