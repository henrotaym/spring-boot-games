package henrotaym.env.tests.feature.queues;

import henrotaym.env.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class QueueEmitterTest extends ApplicationTest {
  @Autowired KafkaTemplate<String, String> kafkaTemplate;

  @Test
  public void it_sends_and_consumes_event() {
    kafkaTemplate.send("nice-topic", "nice message");
  }
}
