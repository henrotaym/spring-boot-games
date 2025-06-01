package henrotaym.env.tests.feature.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import henrotaym.env.ApplicationTest;
import henrotaym.env.events.UserCreatedEvent;
import henrotaym.env.services.events.EventEmitterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AmqpTemplateTest extends ApplicationTest {
  @Autowired EventEmitterService emitter;

  @Test
  public void it_sends_message_to_listener() throws JsonProcessingException {
    this.emitter.dispatch(new UserCreatedEvent(Integer.valueOf("12345")));
    this.emitter.dispatch(new UserCreatedEvent(Integer.valueOf("23456")));
    // this.emitter.dispatch(new UserUpdatedEvent(Integer.valueOf("23456")));
    // this.emitter.dispatch(new UserDeletedEvent(Integer.valueOf("34567")));
    // this.emitter.dispatch(new UserBirthdayEvent(Integer.valueOf("45678")));
  }
}
