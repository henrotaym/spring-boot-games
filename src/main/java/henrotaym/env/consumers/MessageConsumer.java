package henrotaym.env.consumers;

import henrotaym.env.events.UserCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {
  @RetryableTopic(attempts = "1")
  @KafkaListener(topics = "user-created")
  public void consume(UserCreated user) {
    log.info(user.getMessage());
  }
}
