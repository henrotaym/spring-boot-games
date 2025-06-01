package henrotaym.env.listeners;

import henrotaym.env.enums.ProfileName;
import henrotaym.env.events.UserCreatedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({ProfileName.QUEUE, ProfileName.TEST})
public class UserCreatedListener implements Listener<UserCreatedEvent> {
  @Override
  public void handle(UserCreatedEvent data) {
    System.out.println("suce1Q " + data.getBytes());
  }
}
