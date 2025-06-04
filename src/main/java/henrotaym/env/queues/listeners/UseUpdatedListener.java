package henrotaym.env.queues.listeners;

import henrotaym.env.annotations.KafkaRetryableListener;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.queues.events.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile(ProfileName.QUEUE)
public class UseUpdatedListener implements Listener<UserUpdatedEvent> {
  @Override
  @KafkaRetryableListener(UserUpdatedEvent.EVENT_NAME)
  public void listen(UserUpdatedEvent event) {
    log.info("consumed " + event.getMessage());
  }
}
